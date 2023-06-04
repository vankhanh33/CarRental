package com.greenhuecity.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.greenhuecity.data.model.Cars;

import java.util.ArrayList;
import java.util.List;

public class FavoriteCarDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favorite_cars.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CARS = "cars";
    private static final String COLUMN_CAR_ID = "car_id";
    private static final String COLUMN_CAR_IMG = "car_img";
    private static final String COLUMN_CAR_NAME = "car_name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_FROM_TIME = "from_time";
    private static final String COLUMN_END_TIME = "end_time";
    private static final String COLUMN_APPROVE = "approve";
    private static final String COLUMN_POWER_ID = "power_id";
    private static final String COLUMN_POWER_NAME = "power_name";
    private static final String COLUMN_POWER_VALUE = "power_value";
    private static final String COLUMN_SPEC_ID = "spec_id";
    private static final String COLUMN_SPEC_TOP_SPEED = "spec_top_speed";
    private static final String COLUMN_SPEC_HORSE_POWER = "spec_horse_power";
    private static final String COLUMN_SPEC_MILEAGE = "spec_mileage";
    private static final String COLUMN_BRAND_ID = "brand_id";
    private static final String COLUMN_BRAND_IMG = "brand_img";
    private static final String COLUMN_BRAND_NAME = "brand_name";
    private static final String COLUMN_DESTRIBUTOR_ID = "distributor_id";


    // SQL statement to create the cars table
    private static final String CREATE_TABLE_CARS = "CREATE TABLE " + TABLE_CARS + "(" +
            COLUMN_CAR_ID + " INTEGER PRIMARY KEY , " +
            COLUMN_CAR_IMG + " TEXT, " +
            COLUMN_CAR_NAME + " TEXT, " +
            COLUMN_PRICE + " REAL, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_STATUS + " TEXT, " +
            COLUMN_FROM_TIME + " TEXT, " +
            COLUMN_END_TIME + " TEXT, " +
            COLUMN_APPROVE + " TEXT, " +
            COLUMN_POWER_ID + " INTEGER, " +
            COLUMN_POWER_NAME + " TEXT, " +
            COLUMN_POWER_VALUE + " TEXT, " +
            COLUMN_SPEC_ID + " INTEGER, " +
            COLUMN_SPEC_TOP_SPEED + " REAL, " +
            COLUMN_SPEC_HORSE_POWER + " REAL, " +
            COLUMN_SPEC_MILEAGE + " REAL, " +
            COLUMN_BRAND_ID + " INTEGER, " +
            COLUMN_BRAND_IMG + " TEXT, " +
            COLUMN_BRAND_NAME + " TEXT, " +
            COLUMN_DESTRIBUTOR_ID + " INTEGER);";


    public FavoriteCarDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create cars and categories tables
        db.execSQL(CREATE_TABLE_CARS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARS);

        // create fresh tables
        this.onCreate(db);
    }

    // add a car to the database
    public long addCar(Cars car) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CAR_ID, car.getCar_id());
        values.put(COLUMN_CAR_IMG, car.getCar_img());
        values.put(COLUMN_CAR_NAME, car.getCar_name());
        values.put(COLUMN_PRICE, car.getPrice());
        values.put(COLUMN_DESCRIPTION, car.getDescription());
        values.put(COLUMN_STATUS, car.getStatus());
        values.put(COLUMN_FROM_TIME, car.getFrom_time());
        values.put(COLUMN_END_TIME, car.getEnd_time());
        values.put(COLUMN_APPROVE, car.getApprove());
        values.put(COLUMN_POWER_ID, car.getPower_id());
        values.put(COLUMN_POWER_NAME, car.getPower_name());
        values.put(COLUMN_POWER_VALUE, car.getPower_value());
        values.put(COLUMN_SPEC_ID, car.getSpec_id());
        values.put(COLUMN_SPEC_TOP_SPEED, car.getSpec_top_speed());
        values.put(COLUMN_SPEC_HORSE_POWER, car.getSpec_horse_power());
        values.put(COLUMN_SPEC_MILEAGE, car.getSpec_mileage());
        values.put(COLUMN_BRAND_ID, car.getBrand_id());
        values.put(COLUMN_BRAND_IMG, car.getBrand_img());
        values.put(COLUMN_BRAND_NAME, car.getBrand_name());
        values.put(COLUMN_DESTRIBUTOR_ID, car.getDistributor_id());


        // insert row
        long id = db.insert(TABLE_CARS, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }


    // delete a car from the database
    public void deleteCar(long carId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CARS, COLUMN_CAR_ID + " = ?", new String[]{String.valueOf(carId)});
        db.close();
    }

    // get all cars from the database
    public List<Cars> getAllCars() {
        List<Cars> carList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CARS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        while (cursor.moveToNext()) {
            int car_id = cursor.getInt(0);
            String car_img = cursor.getString(1);
            String car_name = cursor.getString(2);
            double price = cursor.getDouble(3);
            String description = cursor.getString(4);
            String status = cursor.getString(5);
            String from_time = cursor.getString(6);
            String end_time = cursor.getString(7);
            String approve = cursor.getString(8);
            int power_id = cursor.getInt(9);
            String power_name = cursor.getString(10);
            String power_value = cursor.getString(11);
            int spec_id = cursor.getInt(12);
            double spec_top_speed = cursor.getDouble(13);
            double spec_horse_power = cursor.getDouble(14);
            double spec_mileage = cursor.getDouble(15);
            int brand_id = cursor.getInt(16);
            String brand_img = cursor.getString(17);
            String brand_name = cursor.getString(18);
            int distributor_id = cursor.getInt(19);
            Cars car = new Cars(car_id, car_img, car_name, price, description, status, from_time, end_time, approve,
                                power_id, power_name, power_value, spec_id, spec_top_speed, spec_horse_power, spec_mileage,
                                brand_id, brand_img, brand_name, distributor_id);
            // Adding car to list
            carList.add(car);
        }


        // close db connection
        cursor.close();
        db.close();

        // return car list
        return carList;
    }


}
