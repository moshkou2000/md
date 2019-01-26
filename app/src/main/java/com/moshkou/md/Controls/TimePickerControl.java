package com.moshkou.md.Controls;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.Formatter;
import android.widget.TextView;

import com.moshkou.md.Adapters.DayMonthAdapter;
import com.moshkou.md.R;

import com.moshkou.md.Configs.Enumerates;

import java.util.Calendar;


public class TimePickerControl extends FrameLayout {

    private Context context;


    /**
     * A no-op callback used in the constructor to avoid null checks
     * later in the code.
     */
    private static final OnTimeChangedListener NO_OP_CHANGE_LISTENER = new OnTimeChangedListener() {
        public void onTimeChanged(TimePickerControl view, Enumerates.ConfirmationState confirmationState, int hourOfDay, int minute) { }
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
    private int mCurrentHour = 0; // 0-23
    private int mCurrentMinute = 0; // 0-59
    private Boolean mIs24HourView = false;
    private boolean mIsAm;

    // ui components
    private final TextView title;
    private final NumberPicker mHourPicker;
    private final NumberPicker mMinutePicker;
    private final NumberPicker mAmPmPicker;
    private final android.support.v7.widget.RecyclerView dayMonth;

    private final DayMonthAdapter dayMonthAdapter;

    // callbacks
    private OnTimeChangedListener mOnTimeChangedListener;

    /**
     * The callback interface used to indicate the time has been adjusted.
     */
    public interface OnTimeChangedListener {

        /**
         * view The view associated with this listener.
         * hourOfDay The current hour.
         * minute The current minute.
         */
        void onTimeChanged(TimePickerControl view, Enumerates.ConfirmationState confirmationState, int hourOfDay, int minute);

        //void onDoneClicked();
    }

    public TimePickerControl(Context context) {
        this(context, null);
    }

    public TimePickerControl(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimePickerControl(final Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_time_picker, this, true);

        title = findViewById(R.id.title);

        // day  month
        dayMonth = findViewById(R.id.dayMonth);
        dayMonth.setHasFixedSize(true);
        dayMonth.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        dayMonthAdapter = new DayMonthAdapter(getContext());
        dayMonth.setAdapter(dayMonthAdapter);

        // hour
        mHourPicker = findViewById(R.id.hour);
        mHourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mCurrentHour = newVal;
                if (!mIs24HourView) {
                    // adjust from [1-12] to [0-11] internally, with the times
                    // written "12:xx" being the start of the half-day
                    if (mCurrentHour == 12) {
                        mCurrentHour = 0;
                    }
                    if (!mIsAm) {
                        // PM means 12 hours later than nominal
                        mCurrentHour += 12;
                    }
                }
                onTimeChanged(Enumerates.ConfirmationState.NULL);
            }
        });

        // digits of minute
        mMinutePicker = findViewById(R.id.minute);
        mMinutePicker.setMinValue(0);
        mMinutePicker.setMaxValue(59);
        mMinutePicker.setFormatter(TWO_DIGIT_FORMATTER);
        mMinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker spinner, int oldVal, int newVal) {
                mCurrentMinute = newVal;
                onTimeChanged(Enumerates.ConfirmationState.NULL);
            }
        });

        // AM   PM
        mAmPmPicker = findViewById(R.id.amPm);
        mAmPmPicker.setMinValue(0);
        mAmPmPicker.setMaxValue(1);
        mAmPmPicker.setFormatter(AM_PM_FORMATTER);
        mAmPmPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker spinner, int oldVal, int newVal) {
                if (mIsAm) {
                    // Currently AM switching to PM
                    if (mCurrentHour < 12) {
                        mCurrentHour += 12;
                    }
                } else {
                    // Currently PM switching to AM
                    if (mCurrentHour >= 12) {
                        mCurrentHour -= 12;
                    }
                }
                mIsAm = !mIsAm;
                onTimeChanged(Enumerates.ConfirmationState.NULL);
            }
        });


        // now that the hour/minute picker objects have been initialized, set
        // the hour range properly based on the 12/24 hour display mode.
        configurePickerRanges();

        // initialize to current time
        Calendar cal = Calendar.getInstance();
        setOnTimeChangedListener(NO_OP_CHANGE_LISTENER);

        // by default we're not in 24 hour mode
        setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
        setCurrentMinute(cal.get(Calendar.MINUTE));

        mIsAm = (mCurrentHour < 12);


        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onTimeChanged(Enumerates.ConfirmationState.CANCEL);
            }
        });
        Button ok = findViewById(R.id.ok);
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
        mMinutePicker.setEnabled(enabled);
        mHourPicker.setEnabled(enabled);
        mAmPmPicker.setEnabled(enabled);
    }

    /**
     * Used to save / restore state of time picker
     */
    private static class SavedState extends BaseSavedState {

        private final int mHour;
        private final int mMinute;

        private SavedState(Parcelable superState, int hour, int minute) {
            super(superState);
            mHour = hour;
            mMinute = minute;
        }

        private SavedState(Parcel in) {
            super(in);
            mHour = in.readInt();
            mMinute = in.readInt();
        }

        public int getHour() {
            return mHour;
        }

        public int getMinute() {
            return mMinute;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(mHour);
            dest.writeInt(mMinute);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Creator<SavedState>() {
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
        return new SavedState(superState, mCurrentHour, mCurrentMinute);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setCurrentHour(ss.getHour());
        setCurrentMinute(ss.getMinute());
    }

    /**
     * Set the callback that indicates the time has been adjusted by the user.
     * onTimeChangedListener the callback, should not be null.
     */
    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        mOnTimeChangedListener = onTimeChangedListener;
    }

    /**
     * The current hour (0-23).
     */
    public Integer getCurrentHour() {
        return mCurrentHour;
    }

    /**
     * Set the current hour.
     */
    public void setCurrentHour(Integer currentHour) {
        this.mCurrentHour = currentHour;
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

    /**
     * true if this is in 24 hour view else false.
     */
    public boolean is24HourView() {
        return mIs24HourView;
    }

    /**
     * The current minute.
     */
    public Integer getCurrentMinute() {
        return mCurrentMinute;
    }

    /**
     * Set the current minute (0-59).
     */
    public void setCurrentMinute(Integer currentMinute) {
        this.mCurrentMinute = currentMinute;
        updateMinuteDisplay();
    }


    @Override
    public int getBaseline() {
        return mHourPicker.getBaseline();
    }

    /**
     * Set the state of the spinners appropriate to the current hour.
     */
    private void updateHourDisplay() {
        int currentHour = mCurrentHour;
        if (!mIs24HourView) {
            // convert [0,23] ordinal to wall clock display
            if (currentHour > 12) currentHour -= 12;
            else if (currentHour == 0) currentHour = 12;
        }
        mHourPicker.setValue(currentHour);
        mIsAm = mCurrentHour < 12;
        mAmPmPicker.setValue(mIsAm ? 0 : 1);
        onTimeChanged(Enumerates.ConfirmationState.NULL);
    }

    private void configurePickerRanges() {
        if (mIs24HourView) {
            mHourPicker.setMinValue(0);
            mHourPicker.setMaxValue(23);
            mHourPicker.setFormatter(TWO_DIGIT_FORMATTER);
            mAmPmPicker.setVisibility(View.GONE);
        } else {
            mHourPicker.setMinValue(1);
            mHourPicker.setMaxValue(12);
            mHourPicker.setFormatter(null);
            mAmPmPicker.setVisibility(View.VISIBLE);
        }
    }

    private void onTimeChanged(Enumerates.ConfirmationState confirmationState) {
        mOnTimeChangedListener.onTimeChanged(this, confirmationState, getCurrentHour(), getCurrentMinute());
    }

    /**
     * Set the state of the spinners appropriate to the current minute.
     */
    private void updateMinuteDisplay() {
        mMinutePicker.setValue(mCurrentMinute);
        mOnTimeChangedListener.onTimeChanged(this, Enumerates.ConfirmationState.NULL, getCurrentHour(), getCurrentMinute());
    }

}