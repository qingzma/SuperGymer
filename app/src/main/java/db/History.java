package db;

import java.util.Date;

/**
 * Created by qingzhi on 07/03/2017.
 */

public class History {
    private int _history_id;
    private int _userId;
    private Date _date;
    private float _calories;

    public History(int _userId, Date _date, float _calories) {
        this._userId = _userId;
        this._date = _date;
        this._calories = _calories;
    }

    public void set_userId(int _userId) {
        this._userId = _userId;
    }

    public void set_date(Date _date) {
        this._date = _date;
    }

    public void set_calories(float _calories) {
        this._calories = _calories;
    }

    public int get_userId() {
        return _userId;
    }

    public Date get_date() {
        return _date;
    }

    public float get_calories() {
        return _calories;
    }

    public int get_history_id() {
        return _history_id;
    }

    public void set_history_id(int _history_id) {
        this._history_id = _history_id;
    }

    public String toString(){
        return "history id: "+_history_id+ "user id: "+_userId+", date: "+_date.toString()+", calories: "+_calories;
    }
}
