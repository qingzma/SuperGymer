package db;

import java.util.Date;

/**
 * Created by qingzhi on 07/03/2017.
 */

public class Equipment {
    private int _equipment_id;
    private String _equipment_name;
    private String _part;
    private String _video_url;
    private String _introduction;
    private String _image_path;

    public Equipment(int _equipment_id, String _equipment_name, String _part, String _video_url, String _introduction, String _image_path) {
        this._equipment_id = _equipment_id;
        this._equipment_name = _equipment_name;
        this._part = _part;
        this._video_url = _video_url;
        this._introduction = _introduction;
        this._image_path = _image_path;
    }

    public Equipment(String _equipment_name, String _part, String _video_url, String _introduction, String _image_path) {
        this._equipment_name = _equipment_name;
        this._part = _part;
        this._video_url = _video_url;
        this._introduction = _introduction;
        this._image_path = _image_path;
    }

    /*
            public Equipment(int _equipment_id, String _equipment_name, String _part, String _video_url, String _introduction) {
                this._equipment_id = _equipment_id;
                this._equipment_name = _equipment_name;
                this._part = _part;
                this._video_url = _video_url;
                this._introduction = _introduction;
            }

            public Equipment(String _equipment_name, String _part, String _video_url, String _introduction) {
                this._equipment_name = _equipment_name;
                this._part = _part;
                this._video_url = _video_url;
                this._introduction = _introduction;
            }
        */
    public int get_equipment_id() {
        return _equipment_id;
    }

    public void set_equipment_id(int _equipment_id) {
        this._equipment_id = _equipment_id;
    }

    public String get_part() {
        return _part;
    }

    public void set_part(String _part) {
        this._part = _part;
    }

    public String get_equipment_name() {
        return _equipment_name;
    }

    public void set_equipment_name(String _equipment_name) {
        this._equipment_name = _equipment_name;
    }

    public String get_video_url() {
        return _video_url;
    }

    public void set_video_url(String _video_url) {
        this._video_url = _video_url;
    }

    public String get_introduction() {
        return _introduction;
    }

    public void set_introduction(String _introduction) {
        this._introduction = _introduction;
    }

    public String get_image_path() {
        return _image_path;
    }

    public void set_image_path(String _image_path) {
        this._image_path = _image_path;
    }

    public String toString(){
        return "euipment id: "+_equipment_id+", name: "+_equipment_name+", _part: "+_part+
                ", URL: "+_video_url+", introduction: "+_introduction+", image path: "+_image_path;
    }
}
