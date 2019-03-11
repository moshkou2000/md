package com.moshkou.md.controls;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.Formatter;
import android.widget.TextView;

import com.moshkou.md.adapters.DayMonthAdapter;
import com.moshkou.md.models.DateModel;
import com.moshkou.md.models.DatetimeModel;
import com.moshkou.md.R;

import com.moshkou.md.configs.Enumerates;

import java.util.Calendar;


public class DateTimePickerControl extends FrameLayout {

    private Context context;


    /**
     * A no-op callback used in the constructor to avoid null checks
     * later in the code.
     */
    private static final OnTimeChangedListener NO_OP_CHANGE_LISTENER = new OnTimeChangedListener() {
        public void onTimeChanged(DateTimePickerControl view, Enumerates.ConfirmationState confirmationState, DatetimeModel datetime) { }
    };

    public static final NumberPicker.Formatter TWO_DIGIT_FORMATTER =
            new Formatter() {

                @Override
                public String format(int value) {
                    return String.format("%02d", value);
                }
            };

    private static String[] AMPM = new String[]{"AM","PM"};
    public static final NumberPicker.Formatter AM_PM_FORMATTER =
            new Formatter() {

                @Override
                public String format(int value) {
                    return AMPM[value];
                }
            };


    // state
    private DatetimeModel datetime = new DatetimeModel();
    private Boolean mIs24HourView = false;
    private boolean isAm = true;
    private boolean dayMonthLock = false;
    private boolean scrollUp = true;
    private int dayMonthItemPosition = 0;


    // ui components
    private final TextView title;
    private final NumberPicker yearPicker;
    private final NumberPicker hourPicker;
    private final NumberPicker minutePicker;
    private final NumberPicker amPmPicker;
    private final android.support.v7.widget.RecyclerView dayMonth;

    private final DayMonthAdapter dayMonthAdapter;


    // callbacks
    private OnTimeChangedListener onTimeChangedListener;

    /**
     * The callback interface used to indicate the time has been adjusted.
     */
    public interface OnTimeChangedListener {

        /**
         * view The view associated with this listener.
         */
        void onTimeChanged(DateTimePickerControl view, Enumerates.ConfirmationState confirmationState, DatetimeModel datetime);
    }


    public DateTimePickerControl(Context context) {
        this(context, null);
    }

    public DateTimePickerControl(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateTimePickerControl(final Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_time_picker, this, true);

        title = findViewById(R.id.title);
        yearPicker = findViewById(R.id.year);
        hourPicker = findViewById(R.id.hour);
        minutePicker = findViewById(R.id.minute);
        dayMonth = findViewById(R.id.dayMonth);


        // year
        yearPicker.setMinValue(1900);
        yearPicker.setMaxValue(2022);
        yearPicker.setValue(Calendar.getInstance().get(Calendar.YEAR));
        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker spinner, int oldVal, int newVal) {
                datetime.setYear(newVal);
                dayMonthAdapter.setValue(datetime.getCalendar());
                onTimeChanged(Enumerates.ConfirmationState.NULL);
            }
        });

        // day  month
        dayMonthAdapter = new DayMonthAdapter(getContext());
        dayMonthAdapter.setOnDataChangedListener(new DayMonthAdapter.OnDataChangedListener() {
            @Override
            public void onDataChanged(boolean flag, int addedSize, Calendar calendar) {
                dayMonthLock = false;

//                if (!dayMonthLock)
//                    dayMonthItemPosition = addedSize;

                setDate(calendar);
            }
        });
        dayMonthAdapter.setOnClickListener(new DayMonthAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
//                LinearLayoutManager lm = (LinearLayoutManager) dayMonth.getLayoutManager();
//                lm.smoothScrollToPosition(dayMonth, null, position + 1);
//
//                Log.i("WWWW", "" + position);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(context) {

                    private static final float SPEED = 1000f;// Change this value (default=25f)

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }
                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }

            @Override
            public void scrollToPosition(int position) {
                super.scrollToPosition(position);
            }
        };

        dayMonth.setHasFixedSize(true);
        dayMonth.setLayoutManager(linearLayoutManager);
        dayMonth.setAdapter(dayMonthAdapter);
        dayMonth.addOnScrollListener(new CustomScrollListener());
        dayMonthItemPosition = DayMonthAdapter.RANGE / 2 - 2;
        LinearLayoutManager lm = (LinearLayoutManager) dayMonth.getLayoutManager();
        lm.scrollToPosition(dayMonthItemPosition);

        // hour
        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int hour = datetime.getHour();
                datetime.setHour(newVal);
                if (!mIs24HourView) {
                    // adjust from [1-12] to [0-11] internally, with the times
                    // written "12:xx" being the start of the half-day
                    if (hour == 12) {
                        datetime.setHour(0);
                    }
                    if (!isAm) {
                        // PM means 12 hours later than nominal
                        datetime.setHour(hour + 12);
                    }
                }
                onTimeChanged(Enumerates.ConfirmationState.NULL);
            }
        });

        // digits of minute
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setFormatter(TWO_DIGIT_FORMATTER);
        minutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker spinner, int oldVal, int newVal) {
                datetime.setMinute(newVal);
                onTimeChanged(Enumerates.ConfirmationState.NULL);
            }
        });

        // AM   PM
        amPmPicker = findViewById(R.id.amPm);
        amPmPicker.setMinValue(0);
        amPmPicker.setMaxValue(1);
        amPmPicker.setFormatter(AM_PM_FORMATTER);
        amPmPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker spinner, int oldVal, int newVal) {
                int hour = datetime.getHour();
                if (isAm) {
                    // Currently AM switching to PM
                    if (hour < 12) {
                        datetime.setHour(hour + 12);
                    }
                } else {
                    // Currently PM switching to AM
                    if (hour >= 12) {
                        datetime.setHour(hour - 12);
                    }
                }
                isAm = !isAm;
                onTimeChanged(Enumerates.ConfirmationState.NULL);
            }
        });


        // now that the hour/minute picker objects have been initialized, set
        // the hour RANGE properly based on the 12/24 hour display mode.
        configurePickerRanges();

        setOnTimeChangedListener(NO_OP_CHANGE_LISTENER);

        isAm = (datetime.getHour() < 12);


        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onTimeChanged(Enumerates.ConfirmationState.CANCEL);
            }
        });
        Button ok = findViewById(R.id.done);
        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onTimeChanged(Enumerates.ConfirmationState.OK);
            }
        });

        if (!isEnabled()) {
            setEnabled(false);
        }
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        yearPicker.setEnabled(enabled);
        hourPicker.setEnabled(enabled);
        minutePicker.setEnabled(enabled);
        amPmPicker.setEnabled(enabled);
        dayMonth.setEnabled(enabled);
    }

    /**
     * Used to save / restore state of time picker
     */
    private static class SavedState extends BaseSavedState {

        private final int year;
        private final int month;
        private final int day;
        private final int hour;
        private final int minute;

        private SavedState(Parcelable superState, DatetimeModel datetime) {
            super(superState);

            this.year = datetime.getYear();
            this.month = datetime.getMonth();
            this.day = datetime.getDay();
            this.hour = datetime.getHour();
            this.minute = datetime.getMinute();
        }

        private SavedState(Parcel in) {
            super(in);
            this.year = in.readInt();
            this.month = in.readInt();
            this.day = in.readInt();
            this.hour = in.readInt();
            this.minute = in.readInt();
        }

        public int getYear() { return year; }
        public int getMonth() { return month; }
        public int getDay() { return day; }
        public int getHour() { return hour; }
        public int getMinute() { return minute; }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(year);
            dest.writeInt(month);
            dest.writeInt(day);
            dest.writeInt(hour);
            dest.writeInt(minute);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, datetime);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setDatetime(ss.getYear(), ss.getMonth(), ss.getDay(), ss.getHour(), ss.getMinute());
    }

    /**
     * Set the callback that indicates the time has been adjusted by the user.
     * onTimeChangedListener the callback, should not be null.
     */
    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        this.onTimeChangedListener = onTimeChangedListener;
    }


    public DatetimeModel getDatetime() {
        return this.datetime;
    }

    public void setDatetime(int year, int month, int day, int hour, int minute) {
        this.datetime.setCalendar(year, month, day, hour, minute);
        updateHourDisplay();
    }

    private void setDate(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        datetime.setYear(year);
        datetime.setMonth(calendar.get(Calendar.MONTH));
        datetime.setDay(calendar.get(Calendar.DATE));
        yearPicker.setValue(year);

        onTimeChanged(Enumerates.ConfirmationState.NULL);
    }
    private void setDate(DateModel date) {
        datetime.setYear(date.getYear());
        datetime.setMonth(date.getMonth());
        datetime.setDay(date.getDay());
        yearPicker.setValue(date.getYear());

        onTimeChanged(Enumerates.ConfirmationState.NULL);
    }


    /**
     * Set whether in 24 hour or AM/PM mode.
     * is24HourView True = 24 hour mode. False = AM/PM.
     */
    public void setIs24HourView(Boolean is24HourView) {
        if (mIs24HourView != is24HourView) {
            mIs24HourView = is24HourView;
            configurePickerRanges();
            updateHourDisplay();
        }
    }

    @Override
    public int getBaseline() {
        return hourPicker.getBaseline();
    }

    /**
     * Set the state of the spinners appropriate to the current hour.
     */
    private void updateHourDisplay() {
        int currentHour = this.datetime.getHour();
        if (!mIs24HourView) {
            // convert [0,23] ordinal to wall clock display
            if (currentHour > 12) currentHour -= 12;
            else if (currentHour == 0) currentHour = 12;
        }
        hourPicker.setValue(currentHour);
        minutePicker.setValue(this.datetime.getMinute());
        isAm = this.datetime.getHour() < 12;
        amPmPicker.setValue(isAm ? 0 : 1);
        dayMonthAdapter.setValue(this.datetime.getCalendar());
        onTimeChanged(Enumerates.ConfirmationState.NULL);
    }

    private void configurePickerRanges() {
        if (mIs24HourView) {
            hourPicker.setMinValue(0);
            hourPicker.setMaxValue(23);
            hourPicker.setFormatter(TWO_DIGIT_FORMATTER);
            amPmPicker.setVisibility(View.GONE);
        } else {
            hourPicker.setMinValue(1);
            hourPicker.setMaxValue(12);
            hourPicker.setFormatter(null);
            amPmPicker.setVisibility(View.VISIBLE);
        }
    }

    private void onTimeChanged(Enumerates.ConfirmationState confirmationState) {
        onTimeChangedListener.onTimeChanged(this, confirmationState, getDatetime());
    }


    public class CustomScrollListener extends RecyclerView.OnScrollListener {
        public CustomScrollListener() { }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                int position = dayMonthAdapter.getItemCount();
                position = scrollUp ? position - 1 : 0;

                if (dayMonthLock) {
                    dayMonthAdapter.addDayMonth(scrollUp, position);
                } else {
                    position = dayMonthItemPosition;
                    setDate(dayMonthAdapter.getItem(dayMonthItemPosition));
                }

                Log.i("WWWWW", "" + position);

                LinearLayoutManager lm = (LinearLayoutManager) dayMonth.getLayoutManager();
                lm.smoothScrollToPosition(dayMonth, null, scrollUp ? position - 2 : position);
            }
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (!dayMonthLock) {
                dayMonthItemPosition = ((LinearLayoutManager)dayMonth.getLayoutManager()).findFirstVisibleItemPosition();

                if (dy > 0) {
                    scrollUp = true;

                    dayMonthItemPosition += 2; // last visible item in the list

                    if (dayMonthItemPosition + 1 >= dayMonthAdapter.getItemCount())
                        dayMonthLock = true;

                } else if (dy < 0) {
                    scrollUp = false;

                    if (dayMonthItemPosition - 1 <= 0)
                        dayMonthLock = true;
                }
                Log.i("WWWWW", ">>" + dayMonthItemPosition);
            }
        }
    }


}