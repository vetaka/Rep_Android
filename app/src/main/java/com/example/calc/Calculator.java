package com.example.calc;


import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


import android.os.Parcel;
import android.os.Parcelable;


public class Calculator implements Parcelable {
    /**
     *
     */
    private String strExpression;
    private String result;

    public Calculator(String strExpression,String result){
        this.strExpression = strExpression;
        this.result=result;

    }

    protected Calculator(Parcel in) {
        result = in.readString();
        strExpression = in.readString();
    }

    public static final Creator<Calculator> CREATOR = new Creator<Calculator>() {
        @Override
        public Calculator createFromParcel(Parcel in) {
            return new Calculator(in);
        }

        @Override
        public Calculator[] newArray(int size) {
            return new Calculator[size];
        }
    };

    public String  Calculate(String strExpression){
        // используем javascript для вычисления выражения
        this.strExpression = strExpression;
        Context rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scriptable = rhino.initStandardObjects();
            this.result = rhino.evaluateString(scriptable,strExpression,"javascript",
                    1,null).toString();
            }
        catch (Exception e){
            this.result="ERR";
            }
        return this.result;
    }
    public String getResult(){
        return this.result;
    }
    public String getStrExpression(){
        return this.strExpression;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(result);
        parcel.writeString(strExpression);
    }
}
