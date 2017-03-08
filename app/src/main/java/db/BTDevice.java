package db;

//import android.graphics.Path;

//import android.graphics.Path;

import java.io.File;
import java.net.URL;
import java.util.UUID;

/**
 * Created by qingzhi on 08/03/2017.
 */
// a class to define a bluetooth device.
public class BTDevice {
    private int _btdevice_id;
    private String _btdevice_name;
    private UUID _uuid;
    private String _image_path;

    public BTDevice(String _btdevice_name, UUID _uuid, String _image_path) {
        this._btdevice_name = _btdevice_name;
        this._uuid = _uuid;
        this._image_path = _image_path;
    }

    public BTDevice(int _btdevice_id, String _btdevice_name, UUID _uuid, String _image_path) {
        this._btdevice_id = _btdevice_id;
        this._btdevice_name = _btdevice_name;
        this._uuid = _uuid;
        this._image_path = _image_path;
    }

    public BTDevice() {
    }

    public int get_btdevice_id() {
        return _btdevice_id;
    }

    public void set_btdevice_id(int _btdevice_id) {
        this._btdevice_id = _btdevice_id;
    }

    public String get_btdevice_name() {
        return _btdevice_name;
    }

    public void set_btdevice_name(String _btdevice_name) {
        this._btdevice_name = _btdevice_name;
    }

    public UUID get_uuid() {
        return _uuid;
    }

    public void set_uuid(UUID _uuid) {
        this._uuid = _uuid;
    }

    public String get_image_path() {
        return _image_path;
    }

    public void set_image_path(String _image_path) {
        this._image_path = _image_path;
    }

    public String toString(){
        return "btdevice_id: "+_btdevice_id+", name: "+_btdevice_name+ ", uuid: "+_uuid+", path: "+_image_path;
    }
    // lx0v
}
