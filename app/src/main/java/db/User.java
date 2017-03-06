package db;

/**
 * Created by qingzhi on 06/03/2017.
 */

public class User {
    private int _userId;
    private String _name;
    private float _weight;
    private float _height;
    private int _gender;            //1 for male, and 0 for female

    public User(int _userId, String _name, float _weight, float _height, int _gender) {
        this._userId = _userId;
        this._name = _name;
        this._weight = _weight;
        this._height = _height;
        this._gender = _gender;
    }

    public int get_userId() {
        return _userId;
    }

    public String get_name() {
        return _name;
    }

    public float get_weight() {
        return _weight;
    }

    public float get_height() {
        return _height;
    }

    public int get_gender() {
        return _gender;
    }

    public void set_userId(int _userId) {
        this._userId = _userId;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_weight(float _weight) {
        this._weight = _weight;
    }

    public void set_height(float _height) {
        this._height = _height;
    }

    public void set_gender(int _gender) {
        this._gender = _gender;
    }
}
