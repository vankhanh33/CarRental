package com.greenhuecity.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.greenhuecity.data.model.Car;

import java.util.ArrayList;
import java.util.List;

public class FavoriteCarDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favorite_cars.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CARS = "cars";
    private static final String COLUMN_CAR_ID = "_id";
    private static final String COLUMN_CAR_IMG = "car_img";
    private static final String COLUMN_CAR_NAME = "car_name";
    private static final String COLUMN_RENTAL_PRICE = "rental_price";
    private static final String COLUMN_MAX_SPEED = "max_speed";
    private static final String COLUMN_HORSEPOWER = "horsepower";
    private static final String COLUMN_MILEAGE = "mileage";
    private static final String COLUMN_CAR_DESCRIPTION = "car_description";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_LESSOR_ID = "lessor_id";
    private static final String COLUMN_CATEGORY_ID = "category_id";
    private static final String COLUMN_CATEGORY_IMG = "category_img";
    private static final String COLUMN_CATEGORY_NAME = "category_name";

    // SQL statement to create the cars table
    private static final String CREATE_TABLE_CARS = "CREATE TABLE " + TABLE_CARS + "(" +
            COLUMN_CAR_ID + " INTEGER PRIMARY KEY , " +
            COLUMN_CAR_IMG + " TEXT, " +
            COLUMN_CAR_NAME + " TEXT, " +
            COLUMN_RENTAL_PRICE + " INTEGER, " +
            COLUMN_MAX_SPEED + " TEXT, " +
            COLUMN_HORSEPOWER + " TEXT, " +
            COLUMN_MILEAGE + " TEXT, " +
            COLUMN_CAR_DESCRIPTION + " TEXT, " +
            COLUMN_LATITUDE + " DECIMAL(10, 6), " +
            COLUMN_LONGITUDE + " DECIMAL(10, 6), " +
            COLUMN_LESSOR_ID + " INTEGER, " +
            COLUMN_CATEGORY_ID + " INTEGER, " +
            COLUMN_CATEGORY_IMG + " TEXT, " +
            COLUMN_CATEGORY_NAME + " TEXT);";


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
    public long addCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CAR_ID, car.getCar_id());
        values.put(COLUMN_CAR_IMG, car.getCar_img());
        values.put(COLUMN_CAR_NAME, car.getCar_name());
        values.put(COLUMN_RENTAL_PRICE, car.getRental_price());
        values.put(COLUMN_MAX_SPEED, car.getMax_speed());
        values.put(COLUMN_HORSEPOWER, car.getHorsepower());
        values.put(COLUMN_MILEAGE, car.getMileage());
        values.put(COLUMN_CAR_DESCRIPTION, car.getCar_description());
        values.put(COLUMN_LATITUDE, car.getLatitude());
        values.put(COLUMN_LONGITUDE, car.getLongitude());
        values.put(COLUMN_LESSOR_ID, car.getLessor_id());
        values.put(COLUMN_CATEGORY_ID, car.getCategory_id());
        values.put(COLUMN_CATEGORY_IMG, car.getCategory_img());
        values.put(COLUMN_CATEGORY_NAME, car.getCategory_name());

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
    public List<Car> getAllCars() {
        List<Car> carList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CARS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String carImg = cursor.getString(1);
            String carName = cursor.getString(2);
            int rentalPrice = cursor.getInt(3);
            String maxSpeed = cursor.getString(4);
            String horsepower = cursor.getString(5);
            String mileage = cursor.getString(6);
            String carDesc = cursor.getString(7);
            double latitude = cursor.getDouble(8);
            double longitude = cursor.getDouble(9);
            int lessorId = cursor.getInt(10);
            int categoryId = cursor.getInt(11);
            String categoryImg = cursor.getString(12);
            String categoryName = cursor.getString(13);

            Car car = new Car(id, carImg, carName, rentalPrice, maxSpeed, horsepower, mileage, carDesc, latitude, longitude, lessorId, categoryId, categoryImg, categoryName);

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
