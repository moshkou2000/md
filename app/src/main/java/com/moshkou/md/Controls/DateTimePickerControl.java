package com.moshkou.md.Controls;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.Formatter;
import android.widget.TextView;

import com.moshkou.md.Adapters.DayMonthAdapter;
import com.moshkou.md.Models.DatetimeModel;
import com.moshkou.md.R;

import com.moshkou.md.Configs.Enumerates;

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
    private boolean dayMonthLoading = true;

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

        // day  month
        dayMonth.setHasFixedSize(true);
        dayMonth.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        dayMonthAdapter = new DayMonthAdapter(getContext());
        dayMonth.setAdapter(dayMonthAdapter);
        dayMonth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!dayMonthLoading) {
                    dayMonthAdapter.addDayMonth();
                }
                return !dayMonthLoading;
            }
        });
        dayMonth.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dayMonthLoading) {
                    if (dy > 0) {

                        int visibleItemCount = 4;
                        int totalItemCount = dayMonthAdapter.getItemCount();
                        int pastVisibleItems = ((LinearLayoutManager)dayMonth.getLayoutManager()).findFirstVisibleItemPosition();
                        Log.i("WWWWW", "" + dy + " " + totalItemCount + " " + pastVisibleItems);

                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            dayMonthLoading = false;
                        }
                    } else {

                        // TODO: scroll-up

                        // DO this for UP (> 0) & DOWN ( < 0)
                        //
                        // remove data from recycleView dataSet
                        // keep the range of 100 data in the list
                        // load more -> remove more
                        // load & remove
                        // load up, remove down
                        // load down, remove up

                    }
                }
            }
        });
        dayMonthAdapter.setOnDataChangedListener(new DayMonthAdapter.OnDataChangedListener() {
            @Override
            public void onDataChanged(boolean success, int addedSize) {
                dayMonthLoading = true;
            }
        });

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
        amPmPicker.setValue(0);
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
        // the hour range properly based on the 12/24 hour display mode.
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
        isAm = this.datetime.getHour() < 12;
        amPmPicker.setValue(isAm ? 0 : 1);
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

}