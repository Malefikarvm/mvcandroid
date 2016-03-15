package co.com.activity.mvcstructure.Components;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Hashtable;

/**
 * Modelo para las bases de datos android
 * Creado por Santiago Posada el 12/09/2015.
 * @since Version 1.0
 */
public abstract class Model<E> {

    private String tableName;
    protected SQLiteDatabase db;
    private String[] rowsName;
    private Hashtable<String, String> table;

    /**
     * Constructor del modelo
     * @param tbl
     */
    protected Model(String tbl){
        tableName = tbl;
        table = new Hashtable();
    }

    /**
     * Crea el modelo trayendolo desde la bd
     */
    public void createModel (){
        Cursor model = db.rawQuery("SELECT * FROM "+tableName, null);
        rowsName = model.getColumnNames();
        for(String row : rowsName){
            table.put(row, row);
        }
        model.close();
    }

    /**
     * Invocado por {@link Model#tableFieldsName()}
     * Obtiene los nombres de los campos
     * @return Los nombres como cadena
     */
    public String getRowsName(){
        String answer = "";
        for(String field : rowsName){
            System.out.println("CAMPO: " + field);
            answer = answer + field;
        }
        return answer;
    }

    /**
     * Invocado por {@link Model#insertIntoField(String, Object)}
     * @param name
     * @param value
     */
    protected void insertInto(String name, E value){
        if(value instanceof String){
            db.execSQL("INSERT INTO " + tableName + " (" + table.get(name) + ") VALUES('" + value + "')");
        }else if((value instanceof Integer) || (value instanceof Float) || (value instanceof Double) ){
            db.execSQL("INSERT INTO "+tableName+" ("+table.get(name)+") VALUES("+value+")");
        }
    }

    /**
     * Invocado por {@link Model#updateRecord(String, Object, int)}
     * @param name
     * @param value
     * @param id
     */
    protected void update(String name, E value, int id){
        if(value instanceof String){
            db.execSQL("UPDATE "+tableName+" SET "+table.get(name)+"='"+value+"' WHERE _id="+id);
        }else if((value instanceof Integer) || (value instanceof Float) || (value instanceof Double) ){
            db.execSQL("UPDATE "+tableName+" SET "+table.get(name)+"="+value+" WHERE _id="+id);
        }
    }

    /**
     * Invocado por {@link Model#deleteRecord(String, Object, int)}
     * @param name
     * @param value
     * @param id
     */
    protected void delete(String name, E value, int id){
        if(value instanceof String){
            db.execSQL("DELETE FROM "+tableName+" WHERE _id="+id+ " AND " +table.get(name)+"='"+value+"'");
        }else if((value instanceof Integer) || (value instanceof Float) || (value instanceof Double) ){
            db.execSQL("DELETE FROM "+tableName+" WHERE _id="+id+ " AND " +table.get(name)+"='"+value+"'");
        }
    }

    /**
     * Invocado por {@link Model#deleteWhere(String, Object)}
     * @param name
     * @param value
     */
    protected void deleteWhereRecord(String name, E value){
        if(value instanceof String){
            db.execSQL("DELETE FROM "+tableName+" WHERE "+table.get(name)+"='"+value+"'");
        }else if((value instanceof Integer) || (value instanceof Float) || (value instanceof Double) ){
            db.execSQL("DELETE FROM "+tableName+" WHERE " +table.get(name)+"='"+value+"'");
        }
    }



    /**
     * Invocado por {@link Model#searchAll()}
     * @return
     */
    protected Hashtable getData(){
        Cursor data = db.rawQuery("SELECT * FROM "+tableName, null);
        Hashtable<Integer, Hashtable> query = new Hashtable();
        int row = 0;
        while(data.moveToNext()){
            Hashtable<String, String> value = new Hashtable();
            for(int i=0; i<rowsName.length; i++){
                if(data.getString(data.getColumnIndex(rowsName[i]))!=null){
                    value.put(rowsName[i],
                            data.getString(data.getColumnIndex(rowsName[i])));
                }
            }
            query.put(row, value);
            row++;
        }
        data.close();
        return query;
    }

    /**
     * Invocado por {@link Model#search(String, String, Object)}
     * @param name
     * @param condition
     * @param value
     * @return
     */
    protected String selectWhere(String name, String condition, E value){
        String val="";
        Cursor where = null;
        if(value instanceof String) {
            where = db.rawQuery("SELECT " + name + " FROM " + tableName + " WHERE " + condition + "='" + value+ "'", null);
        }else if((value instanceof Integer) || (value instanceof Float) || (value instanceof Double)){
            where = db.rawQuery("SELECT " + name + " FROM " + tableName + " WHERE " + condition + "=" + value, null);
        }
        while (where.moveToNext()){
            val = where.getString(0);
        }
        where.close();
        return val;
    }

    /**
     * Invocado por {@link Model#updateWhereRecord(String, Object, String, Object)}
     * @param name
     * @param value
     * @param condition
     * @param valueCondition
     */
    protected void updateWhere(String name, E value, String condition, E valueCondition){
        if(value instanceof String) {
            db.execSQL("UPDATE " + tableName + "  SET " + name + "= '" + value + "' WHERE " + condition + "" +
                    " = '" + valueCondition+"'");
        }else if((value instanceof Integer) || (value instanceof Float) || (value instanceof Double)) {
            db.execSQL("UPDATE " + tableName + "  SET " + name + "= " + value + " WHERE " + condition + "" +
                    " = " + valueCondition);
        }

    }

    /**
     * Invocado por {@link Model#sumWhereRecord(String, String, Object)}
     * @param name
     * @param condition
     * @param value
     * @return {@link String}
     */
    protected String sumWhere(String name, String condition, E value){
        String val="";
        Cursor where = null;
        if(value instanceof String) {
            where = db.rawQuery("SELECT SUM (" + name + ") FROM " + tableName + " WHERE " + condition + "='" + value+ "'", null);
        }else if((value instanceof Integer) || (value instanceof Float) || (value instanceof Double)){
            where = db.rawQuery("SELECT  SUM (" + name + ") FROM " + tableName + " WHERE " + condition + "=" + value, null);
        }
        while (where.moveToNext()){
            val = where.getString(0);
        }
        where.close();
        return val;
    }

    /**
     * Invocado por {@link Model#countRecords()}
     * @return {@link Integer}
     */
    protected int count(){
        Cursor data = db.rawQuery("SELECT * FROM "+tableName, null);
        return data.getCount();
    }

    /**
     * Invocado por {@link Model#truncateTable()}
     */
    protected void truncate(){
        db.execSQL("DELETE FROM " + tableName+"; VACUUM;");
    }

    /**
     * Invocado por {@link Model#insertInto(String, Object)}
     * @param name
     * @param value
     */
    public void insertIntoTable(String name, E... value){
        String[] rows = name.trim().split(",");
        if((rows.length==value.length)){
            ContentValues values = new ContentValues();
            for(int i=0; i<value.length; i++){
                if(value[i] instanceof String){
                    values.put(rows[i], (String) value[i]);
                }else if(value[i] instanceof Integer){
                    values.put(rows[i], (Integer) value[i]);
                }else if(value[i] instanceof Float){
                    values.put(rows[i], (Float) value[i]);
                }else if(value[i] instanceof Double){
                    values.put(rows[i], (Double) value[i]);
                }
            }
            try {
                db.insert(tableName, null, values);
            }catch(Exception e){
                Log.e("ERROR EN EL MODELO " + tableName, "Los campos son diferentes a los existentes en la tabla " + tableName);
            }
        }else{
            Log.e("ERROR EN EL MODELO "+tableName,"Los campos y los valores a insertar no coinciden");
        }
    }

    /**
     * Invocado por {@link Model#insertIntoWhere(String, String, Object, Object[])}
     * @param name
     * @param condition
     * @param valueCondition
     * @param value
     */
    public void insertIntoTableWhere(String name, String condition, E valueCondition, E... value){
        String[] rows = name.trim().split(",");
        if((rows.length==value.length)){
            String insertion = new String();
            for(int i=0; i<value.length; i++){
                if(value[i] instanceof  String){
                    insertion += "'"+value[i]+"',";
                } else if((value[i] instanceof Integer) || (value[i] instanceof Float) || (value[i] instanceof Double)){
                    insertion += value[i]+",";
                }
            }
            if(valueCondition instanceof String){
                db.execSQL("INSERT INTO "+tableName+" ("+name+") VALUES("+ insertion.substring(0, insertion.length()-1)+") WHERE " +
                        condition+"='"+valueCondition+"'");
            } else if((valueCondition instanceof Integer) || (valueCondition instanceof Float) || (valueCondition instanceof Double)){
                db.execSQL("INSERT INTO "+tableName+" ("+name+") VALUES("+ insertion.substring(0, insertion.length()-1)+") WHERE " +
                        condition+"="+valueCondition);
            }

        }else{
            Log.e("ERROR EN EL MODELO "+tableName,"Los campos y los valores a insertar no coinciden");
        }
    }

    //TODO interfaz

    /**
     * Crea el modelo para la tabla {@link Model#tableName}
     */
    abstract public void generate();

    /**
     * Inserta un registro en la tabla {@link Model#tableName}
     * @param name
     * @param value
     */
    abstract public void insertIntoField(String name, E value);

    /**
     * SELECT * FROM {@link Model#tableName}
     * @return
     */
    abstract public Hashtable searchAll();

    /**
     * Busca un registro en la tabla {@link Model#tableName}
     * @param name SELECT FROM
     * @param condition WHERE
     * @param value =
     * @return el valor
     */
    abstract public String search(String name, String condition, E value);

    /**
     * Muestra los campos de la bd
     * @return el nombre de los campos
     */
    public abstract String tableFieldsName();

    /**
     * Actualiza un registro en la bd según su id
     * @param name
     * @param value
     * @param id
     */
    public abstract void updateRecord(String name, E value, int id);

    /**
     * Elimina el registro
     * @param name
     * @param value
     * @param id
     */
    public abstract void deleteRecord(String name, E value, int id);

    /**
     * Se cuentan el numero de registros en la tabla
     * @return El numero de registros
     */
    public abstract int countRecords();

    /**
     * Vacía todos los registros de la tabla {@link Model#tableName}
     */
    public abstract void truncateTable();

    /**
     * Actualiza un registro que cumpla una condición
     * @param name
     * @param value
     * @param condition
     * @param valueCondition
     */
    public abstract void updateWhereRecord(String name, Object value, String condition, Object valueCondition );

    /**
     * Borra un registro que cumpla una condición
     * @param name
     * @param value
     */
    public abstract void deleteWhere(String name, Object value);

    /**
     * Suma los registros de un campo donde se cumpla una condición
     * @param name
     * @param condition
     * @param value
     * @return
     */
    public abstract String sumWhereRecord(String name, String condition, E value);

    /**
     * Inserta un registro nuevo en la tabla {@link Model#tableName}
     * @param name
     * @param value
     */
    public abstract void insertInto(String name, E... value);

    /**
     * Inserta un registro nuevo en la tabla {@link Model#tableName} que cumpla una condition
     * @param name
     * @param value
     */
    public abstract void insertIntoWhere(String name, String condition, E valueCondition, E... value);
}
