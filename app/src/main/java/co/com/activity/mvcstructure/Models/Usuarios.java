package co.com.activity.mvcstructure.Models;

import android.database.sqlite.SQLiteDatabase;

import java.util.Hashtable;

import co.com.activity.mvcstructure.Components.Model;

/**
 * Created by Analista on 14/01/2016.
 */
public class Usuarios extends Model {

    private SQLiteDatabase db;

    public Usuarios(SQLiteDatabase db) {
        super("usuarios");
        this.db = db;
        generate();
    }

    @Override
    public void generate() {
        super.db = db;
        createModel();
    }

    @Override
    public void insertIntoField(String name, Object value) {
        insertInto(name, value);
    }

    @Override
    public Hashtable searchAll() {
        return getData();
    }

    @Override
    public String search(String name, String condition, Object value) {
        return selectWhere(name, condition, value);
    }

    @Override
    public String tableFieldsName() {
        return getRowsName();
    }

    @Override
    public void updateRecord(String name, Object value, int id) {
        update(name, value, id);
    }

    @Override
    public void deleteRecord(String name, Object value, int id) {
        delete(name, value, id);
    }

    @Override
    public int countRecords() {
        return count();
    }

    @Override
    public void truncateTable() {
        truncate();
    }

    @Override
    public void updateWhereRecord(String name, Object value, String condition, Object valueCondition) {
        updateWhere(name, value, condition, valueCondition);
    }

    @Override
    public void deleteWhere(String name, Object value) {
        deleteWhereRecord(name, value);
    }

    @Override
    public String sumWhereRecord(String name, String condition, Object value) {
        return sumWhere(name, condition, value);
    }

    @Override
    public void insertInto(String name, Object... value) {
        insertIntoTable(name, value);
    }

    @Override
    public void insertIntoWhere(String name, String condition, Object valueCondition, Object... value) {
        insertIntoTableWhere(name, condition, valueCondition, value);
    }
}
