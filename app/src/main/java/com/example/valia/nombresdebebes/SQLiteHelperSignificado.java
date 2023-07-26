package com.example.valia.nombresdebebes;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Valia on 4/1/2020.
 */

public class SQLiteHelperSignificado extends SQLiteOpenHelper {

    private static final String DATABASE_TABLE = "nombres";

    private static String DB_PATH = "/data/data/com.example.valia.nombresdebebes/databases/";

    private static String DB_NAME = "nombre_sign_2.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    //Establecemos los nombres de las columnas
    public static final String KEY_ID = "ID";
    public final static String KEY_COL1 = "NOMBRE";
    public final static String KEY_COL2 = "ORIGEN";
    public final static String KEY_COL3 = "SIGNIFICADO";
    public final static String KEY_COL4 = "SEXO";

    //Array de strings para su uso en los diferentes métodos
    private static final String[] cols = new String[] { KEY_ID, KEY_COL1, KEY_COL2, KEY_COL3, KEY_COL4 };


    public SQLiteHelperSignificado(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {}

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}


    public void open() throws SQLException {
        try {
            if (!checkDataBase()) {
                deployDatabase();
                //Toast.makeText(myContext, "deployDatabase", Toast.LENGTH_SHORT).show();
            }
            /*else
                Toast.makeText(myContext, "se conecto con la bd",Toast.LENGTH_SHORT).show();*/
        } catch (IOException e) {
            e.printStackTrace();
        }

        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }


    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }


    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            //si llegamos aqui es porque la base de datos no existe todavía.
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }


    //Copiar base de datos para directorio predefinido
    public void deployDatabase() throws IOException {
        String packageName = myContext.getApplicationContext().getPackageName();
        String DB_PATH = "/data/data/" + packageName + "/databases/";
        File directory = new File(DB_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        InputStream myInput = myContext.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }


    public PersonaSign getPersona(long _rowIndex) {
        PersonaSign persona = new PersonaSign("","","","",false);
        Cursor result = myDataBase.query(true, DATABASE_TABLE, cols,
                KEY_ID + "=" + _rowIndex, null, null, null, null, null);
        if ((result.getCount() == 0) || !result.moveToFirst()) {
            persona = new PersonaSign("","","","",false);

        } else {
            if (result.moveToFirst()) {
                persona = new PersonaSign(
                        result.getString(result.getColumnIndex(KEY_COL1)),
                        result.getString(result.getColumnIndex(KEY_COL2)),
                        result.getString(result.getColumnIndex(KEY_COL3)),
                        result.getString(result.getColumnIndex(KEY_COL4)),
                        false
                );
            }
        }
        return persona;
    }


    public ArrayList<PersonaSign> getPersonas() {
        ArrayList<PersonaSign> personas = new ArrayList<>();
        Cursor result = myDataBase.query(DATABASE_TABLE,
                cols, null, null, null, null, KEY_ID);
        if (result.moveToFirst())
            do {
                personas.add(new PersonaSign(
                                result.getString(result.getColumnIndex(KEY_COL1)),
                                result.getString(result.getColumnIndex(KEY_COL2)),
                                result.getString(result.getColumnIndex(KEY_COL3)),
                                result.getString(result.getColumnIndex(KEY_COL4)),
                                false
                        )
                );
            } while(result.moveToNext());
        return personas;
    }

}
