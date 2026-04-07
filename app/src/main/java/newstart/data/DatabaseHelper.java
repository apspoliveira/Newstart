package newstart.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * This class contains all methods to access the database.
     */

    private Context context;
    private static final String DATABASE_NAME = "gymtonicapp.db";
    private static final int DATABASE_VERSION = 48;

    // Table foods
    private static final String TABLE_PM = "preset_meals";
    private static final String COL_PM_INDEX = "meal_index";
    private static final String COL_PM_NAME = "name";
    private static final String COL_PM_CATEGORY = "category";
    private static final String COL_PM_CALORIES = "calories";
    private static final String COL_PM_FAT = "fat";
    private static final String COL_PM_FAT_SAT = "fat_sat";
    private static final String COL_PM_CARBS = "carbs";
    private static final String COL_PM_SUGAR = "sugar";
    private static final String COL_PM_PROTEIN = "protein";
    private static final String COL_PM_SALT = "salt";
    private static final String COL_PM_FIBER = "fiber";
    private static final String COL_PM_CHOL = "cholesterin";  // in mg
    private static final String COL_PM_CREATINE = "creatine";
    private static final String COL_PM_CA = "calcium";  // in mg
    private static final String COL_PM_FE = "iron";  // in mg
    private static final String COL_PM_K = "kalium";  // in mg
    private static final String COL_PM_MG = "magnesium";  // in mg
    private static final String COL_PM_MN = "mangan";  // in mg
    private static final String COL_PM_NA = "natrium";  // in mg
    private static final String COL_PM_P = "phosphor";  // in mg
    private static final String COL_PM_ZN = "zinc";  // in mg
    private static final String COL_PM_VIT_A = "vit_a"; // in mg
    private static final String COL_PM_VIT_B1 = "vit_b1"; // in mg
    private static final String COL_PM_VIT_B2 = "vit_b2"; // in mg
    private static final String COL_PM_VIT_B3 = "vit_b3";  // (Niacin) in mg
    private static final String COL_PM_VIT_B5 = "vit_b5";  // (Pantothensäure) in mg
    private static final String COL_PM_VIT_B6 = "vit_b6";  // in mg
    private static final String COL_PM_VIT_B7 = "vit_b7";  // (Biotin) in mg
    private static final String COL_PM_VIT_B11 = "vit_b11";  // (Folsäure, B9) in mg
    private static final String COL_PM_VIT_B12 = "vit_b12";  // in mg
    private static final String COL_PM_VIT_C = "vit_c";  // in mg
    private static final String COL_PM_VIT_E = "vit_e";  // in mg
    private static final String COL_PM_VIT_K = "vit_k";  // in mg
    private static final String COL_PM_VIT_H = "vit_h";  // (Biotin) in mg

    private static final String TABLE_PMC = "meal_categories";
    private static final String COL_PMC_NAME = "name";

    // Table meals per day
    private static final String TABLE_CM = "consumed_meals";
    private static final String COL_CM_DATE = "date";
    private static final String COL_CM_INDEX = "meal_index";  // Refers to uuid-index of a food from foods-table
    private static final String COL_CM_AMOUNT = "amount";

    // Table body-data
    private static final String TABLE_BD = "bodydata";
    private static final String COLUMN_BD_DATE = "date";
    private static final String COLUMN_BD_WEIGHT = "weight";
    private static final String COLUMN_BD_CHEST = "chest";
    private static final String COLUMN_BD_BELLY = "belly";
    private static final String COLUMN_BD_BUTT = "butt";
    private static final String COLUMN_BD_WAIST = "waist";
    private static final String COLUMN_BD_ARM_R = "arm_right";
    private static final String COLUMN_BD_ARM_L = "arm_left";
    private static final String COLUMN_BD_LEG_R = "leg_right";
    private static final String COLUMN_BD_LEG_L = "leg_left";

    // Table exercises
    private static final String TABLE_WP = "workout_plans";
    private static final String COL_WP_NAME = "plan_name";

    private static final String TABLE_WR = "workout_routines";
    private static final String COL_WR_PLAN_NAME = "plan_name";
    private static final String COL_WR_ROUTINE_NAME = "routine_name";

    private static final String TABLE_WE = "exercises";
    private static final String COL_WE_PLAN_NAME = "plan_name";
    private static final String COL_WE_ROUTINE_NAME = "routine";
    private static final String COL_WE_EXERCISE_NAME = "exercise_name";
    private static final String COL_WE_SETS = "sets";
    private static final String COL_WE_REPETITIONS = "repetitions";
    private static final String COL_WE_WEIGHT = "weight";

    // Table settings
    private static final String TABLE_S_GOAL = "settings_goals";
    private static final String COL_S_INDEX = "settings_index";
    private static final String COL_S_GOAL_CALORIES = "goal_calories";
    private static final String COL_S_GOAL_FAT = "goal_fat";
    private static final String COL_S_GOAL_CARBS = "goal_carbs";
    private static final String COL_S_GOAL_PROTEIN = "goal_protein";

    private static final String TABLE_S_LANG = "settings_lang";
    private static final String COL_S_LANG = "language";

    // Table recipes/articles
    private static final String TABLE_RECIPES = "recipes";
    private static final String COL_RECIPE_TITLE = "title";
    private static final String COL_RECIPE_CONTENT = "content";


    // Constructor ---------------------------------------------------------------------------------
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Class default overwrite methods -------------------------------------------------------------

    /** This method will be called upon creation of the database. This method will create all the
     * necessary tables inside the database and prepopulate some tables.
     *
     * @param sqLiteDatabase: SQLiteDatabase that is created
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create table body-data
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABLE_BD + " ("
                        + COLUMN_BD_DATE + " TEXT PRIMARY KEY, "
                        + COLUMN_BD_WEIGHT + " REAL, "
                        + COLUMN_BD_CHEST + " REAL, "
                        + COLUMN_BD_BELLY + " REAL, "
                        + COLUMN_BD_BUTT + " REAL, "
                        + COLUMN_BD_WAIST + " REAL, "
                        + COLUMN_BD_ARM_R + " REAL, "
                        + COLUMN_BD_ARM_L + " REAL, "
                        + COLUMN_BD_LEG_R + " REAL, "
                        + COLUMN_BD_LEG_L + " REAL);"
        );

        // Create table food-data
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABLE_PM + " ("
                        + COL_PM_INDEX + " TEXT PRIMARY KEY, "
                        + COL_PM_NAME + " TEXT, "
                        + COL_PM_CATEGORY + " TEXT, "
                        + COL_PM_CALORIES + " REAL, "
                        + COL_PM_FAT + " REAL, "
                        + COL_PM_FAT_SAT + " REAL, "
                        + COL_PM_CARBS + " REAL, "
                        + COL_PM_SUGAR + " REAL, "
                        + COL_PM_PROTEIN + " REAL, "
                        + COL_PM_SALT + " REAL, "
                        + COL_PM_FIBER + " REAL, "
                        + COL_PM_CHOL + " REAL, "
                        + COL_PM_CREATINE + " REAL, "
                        + COL_PM_CA + " REAL, "
                        + COL_PM_FE + " REAL, "
                        + COL_PM_K + " REAL, "
                        + COL_PM_MG + " REAL, "
                        + COL_PM_MN + " REAL, "
                        + COL_PM_NA + " REAL, "
                        + COL_PM_P + " REAL, "
                        + COL_PM_ZN + " REAL, "
                        + COL_PM_VIT_A + " REAL, "
                        + COL_PM_VIT_B1 + " REAL, "
                        + COL_PM_VIT_B2 + " REAL, "
                        + COL_PM_VIT_B3 + " REAL, "
                        + COL_PM_VIT_B5 + " REAL, "
                        + COL_PM_VIT_B6 + " REAL, "
                        + COL_PM_VIT_B7 + " REAL, "
                        + COL_PM_VIT_B11 + " REAL, "
                        + COL_PM_VIT_B12 + " REAL, "
                        + COL_PM_VIT_C + " REAL, "
                        + COL_PM_VIT_E + " REAL, "
                        + COL_PM_VIT_K + " REAL, "
                        + COL_PM_VIT_H + " REAL);"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PMC + " (" + COL_PMC_NAME + " TEXT PRIMARY KEY);");

        // Create table dailymeals
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CM + " ("
                + COL_CM_DATE + " TEXT, "
                + COL_CM_INDEX + " TEXT, "
                + COL_CM_AMOUNT + " REAL, " +
                "PRIMARY KEY (" + COL_CM_INDEX + ", " + COL_CM_AMOUNT + "));"
        );

        // Create tables exercises
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_WE + " ("
                + COL_WE_PLAN_NAME + " TEXT, "
                + COL_WE_ROUTINE_NAME + " TEXT, "
                + COL_WE_EXERCISE_NAME + " TEXT, "
                + COL_WE_SETS + " INTEGER, "
                + COL_WE_REPETITIONS + " INTEGER, "
                + COL_WE_WEIGHT + " REAL"
                + ");");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_WP + " ("+ COL_WP_NAME + " TEXT PRIMARY KEY);");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_WR + " ("+ COL_WR_PLAN_NAME + " TEXT, " + COL_WR_ROUTINE_NAME + " TEXT, PRIMARY KEY (" + COL_WR_PLAN_NAME + ", " + COL_WR_ROUTINE_NAME + "));");

        // Create table settings
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_S_GOAL + " ("
                + COL_S_INDEX + " INTEGER PRIMARY KEY, "
                + COL_S_GOAL_CALORIES + " REAL, "
                + COL_S_GOAL_FAT + " REAL, "
                + COL_S_GOAL_CARBS + " REAL, "
                + COL_S_GOAL_PROTEIN + " REAL);");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_S_LANG + " ("
                + COL_S_INDEX + " INTEGER PRIMARY KEY, "
                + COL_S_LANG + " TEXT);");

        // Create table recipes
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_RECIPES + " ("
                + COL_RECIPE_TITLE + " TEXT PRIMARY KEY, "
                + COL_RECIPE_CONTENT + " TEXT);");

        // Add preset data to tables ---------------------------------------------------------------

        // Preset meals
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_PM + " VALUES('000000000', 'Apple (100 g)', 'Fruits and Vegetables', 52, 0.17, 0, 13.81, 10.39, 0.26, 0, 2.4, 0, 0, 6, 0.12, 107, 5, 0.035, 1, 11, 0.04, 0.003, 0.017, 0.026, 0.091, 0.061, 0.041, 0, 0, 0, 4.6, 0.18, 0.022, 0)");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_PM + " VALUES('000000001', 'Banana (100 g)', 'Fruits and Vegetables', 95.0, 0.33, 0.0, 22.84, 12.23, 1.0, 0.0, 2.6, 0.0, 0.0, 5.0, 0.26, 358.0, 27.0, 0.0, 0.0, 22.0, 0.15, 0.003, 0.031, 0.073, 0.665, 0.334, 0.367, 0.0, 0.0, 0.0, 8.7, 0.0, 0.0, 0.0)");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_PM + " VALUES('000000002', 'Watermelon (100 g)', 'Fruits and Vegetables', 38.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)");

        // Add categories
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_PMC + " VALUES('Fruits and Vegetables')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_PMC + " VALUES('Meat and Fish')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_PMC + " VALUES('Dairy and Eggs')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_PMC + " VALUES('Bakery and Grains')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_PMC + " VALUES('Drinks')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_PMC + " VALUES('Others')");

        // Add initial recipes
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RECIPES + " VALUES('Garden Lentil Stew', '<h1>Garden Lentil Stew</h1><p>A hearty and nutritious stew packed with lentils and fresh vegetables.</p><h3>Ingredients</h3><ul><li>1 cup brown lentils</li><li>1 onion, chopped</li><li>2 carrots, sliced</li><li>2 stalks celery, sliced</li><li>3 cloves garlic, minced</li><li>1 can diced tomatoes</li><li>4 cups vegetable broth</li><li>1 tsp thyme</li><li>1 tsp oregano</li><li>Salt and pepper to taste</li><li>2 cups spinach</li></ul><h3>Instructions</h3><ol><li>In a large pot, sauté onion, carrots, and celery until softened.</li><li>Add garlic and cook for 1 minute.</li><li>Stir in lentils, tomatoes, broth, thyme, and oregano.</li><li>Bring to a boil, then reduce heat and simmer for 30-40 minutes until lentils are tender.</li><li>Stir in spinach and cook until wilted.</li><li>Season with salt and pepper.</li></ol>');");

        // Add initial workout plan and routines
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_WP + " VALUES('Example Workout Plan');");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_WR + " VALUES('Example Workout Plan', 'LEG DAY');");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_WR + " VALUES('Example Workout Plan', 'PULL DAY');");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_WR + " VALUES('Example Workout Plan', 'PUSH DAY');");

        // Add default settings
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_S_LANG + " VALUES(1, 'en');");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_S_GOAL + " VALUES(1, 2000, 65, 250, 50);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 48) {
            // Ensure all tables exist
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_RECIPES + " ("
                    + COL_RECIPE_TITLE + " TEXT PRIMARY KEY, "
                    + COL_RECIPE_CONTENT + " TEXT);");

            db.execSQL("INSERT OR IGNORE INTO " + TABLE_RECIPES + " VALUES('Garden Lentil Stew', '<h1>Garden Lentil Stew</h1><p>A hearty and nutritious stew packed with lentils and fresh vegetables.</p><h3>Ingredients</h3><ul><li>1 cup brown lentils</li><li>1 onion, chopped</li><li>2 carrots, sliced</li><li>2 stalks celery, sliced</li><li>3 cloves garlic, minced</li><li>1 can diced tomatoes</li><li>4 cups vegetable broth</li><li>1 tsp thyme</li><li>1 tsp oregano</li><li>Salt and pepper to taste</li><li>2 cups spinach</li></ul><h3>Instructions</h3><ol><li>In a large pot, sauté onion, carrots, and celery until softened.</li><li>Add garlic and cook for 1 minute.</li><li>Stir in lentils, tomatoes, broth, thyme, and oregano.</li><li>Bring to a boil, then reduce heat and simmer for 30-40 minutes until lentils are tender.</li><li>Stir in spinach and cook until wilted.</li><li>Season with salt and pepper.</li></ol>');");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_WP + " ("+ COL_WP_NAME + " TEXT PRIMARY KEY);");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_WR + " ("+ COL_WR_PLAN_NAME + " TEXT, " + COL_WR_ROUTINE_NAME + " TEXT, PRIMARY KEY (" + COL_WR_PLAN_NAME + ", " + COL_WR_ROUTINE_NAME + "));");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_WE + " ("
                    + COL_WE_PLAN_NAME + " TEXT, "
                    + COL_WE_ROUTINE_NAME + " TEXT, "
                    + COL_WE_EXERCISE_NAME + " TEXT, "
                    + COL_WE_SETS + " INTEGER, "
                    + COL_WE_REPETITIONS + " INTEGER, "
                    + COL_WE_WEIGHT + " REAL"
                    + ");");
            
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_S_GOAL + " ("
                    + COL_S_INDEX + " INTEGER PRIMARY KEY, "
                    + COL_S_GOAL_CALORIES + " REAL, "
                    + COL_S_GOAL_FAT + " REAL, "
                    + COL_S_GOAL_CARBS + " REAL, "
                    + COL_S_GOAL_PROTEIN + " REAL);");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_S_LANG + " ("
                    + COL_S_INDEX + " INTEGER PRIMARY KEY, "
                    + COL_S_LANG + " TEXT);");

            db.execSQL("INSERT OR IGNORE INTO " + TABLE_WP + " VALUES('Example Workout Plan');");
            db.execSQL("INSERT OR IGNORE INTO " + TABLE_WR + " VALUES('Example Workout Plan', 'LEG DAY');");
            db.execSQL("INSERT OR IGNORE INTO " + TABLE_WR + " VALUES('Example Workout Plan', 'PULL DAY');");
            db.execSQL("INSERT OR IGNORE INTO " + TABLE_WR + " VALUES('Example Workout Plan', 'PUSH DAY');");

            db.execSQL("INSERT OR IGNORE INTO " + TABLE_S_LANG + " VALUES(1, 'en');");
            db.execSQL("INSERT OR IGNORE INTO " + TABLE_S_GOAL + " VALUES(1, 2000, 65, 250, 50);");
        }
    }

    public void addDataBody(String date, double weight, double chest, double belly, double butt,
                            double waist, double arm_r, double arm_l, double leg_r, double leg_l) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BD_DATE, date);
        cv.put(COLUMN_BD_WEIGHT, weight);
        cv.put(COLUMN_BD_CHEST, chest);
        cv.put(COLUMN_BD_BELLY, belly);
        cv.put(COLUMN_BD_BUTT, butt);
        cv.put(COLUMN_BD_WAIST, waist);
        cv.put(COLUMN_BD_ARM_R, arm_r);
        cv.put(COLUMN_BD_ARM_L, arm_l);
        cv.put(COLUMN_BD_LEG_R, leg_r);
        cv.put(COLUMN_BD_LEG_L, leg_l);

        long result = db.replace(TABLE_BD, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to add body data", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getPresetMealCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PMC, null);
    }

    public Cursor getPresetMealsSimpleAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COL_PM_INDEX + ", " + COL_PM_NAME + ", " + COL_PM_CALORIES + " FROM " + TABLE_PM + " ORDER BY " + COL_PM_NAME + " ASC", null);
    }

    public Cursor getPresetMealsSimpleFromCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COL_PM_INDEX + ", " + COL_PM_NAME + ", " + COL_PM_CALORIES + " FROM " + TABLE_PM + " WHERE " + COL_PM_CATEGORY + " = ? ORDER BY " + COL_PM_NAME + " ASC", new String[]{category});
    }

    public void addOrReplaceConsumedMeal(String date, String mealUUID, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_CM_DATE, date);
        cv.put(COL_CM_INDEX, mealUUID);
        cv.put(COL_CM_AMOUNT, amount);
        db.replace(TABLE_CM, null, cv);
    }

    public Cursor getPresetMealDetails(String uuid) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PM + " WHERE " + COL_PM_INDEX + " = ?", new String[]{uuid});
    }

    public void addOrReplacePresetMeal(String uuid, String name, String category, double[] data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_PM_INDEX, uuid);
        cv.put(COL_PM_NAME, name);
        cv.put(COL_PM_CATEGORY, category);
        cv.put(COL_PM_CALORIES, data[0]);
        cv.put(COL_PM_FAT, data[1]);
        cv.put(COL_PM_FAT_SAT, data[2]);
        cv.put(COL_PM_CARBS, data[3]);
        cv.put(COL_PM_SUGAR, data[4]);
        cv.put(COL_PM_PROTEIN, data[5]);
        cv.put(COL_PM_SALT, data[6]);
        cv.put(COL_PM_FIBER, data[7]);
        cv.put(COL_PM_CHOL, data[8]);
        cv.put(COL_PM_CREATINE, data[9]);
        cv.put(COL_PM_CA, data[10]);
        cv.put(COL_PM_FE, data[11]);
        cv.put(COL_PM_K, data[12]);
        cv.put(COL_PM_MG, data[13]);
        cv.put(COL_PM_MN, data[14]);
        cv.put(COL_PM_NA, data[15]);
        cv.put(COL_PM_P, data[16]);
        cv.put(COL_PM_ZN, data[17]);
        cv.put(COL_PM_VIT_A, data[18]);
        cv.put(COL_PM_VIT_B1, data[19]);
        cv.put(COL_PM_VIT_B2, data[20]);
        cv.put(COL_PM_VIT_B3, data[21]);
        cv.put(COL_PM_VIT_B5, data[22]);
        cv.put(COL_PM_VIT_B6, data[23]);
        cv.put(COL_PM_VIT_B7, data[24]);
        cv.put(COL_PM_VIT_B11, data[25]);
        cv.put(COL_PM_VIT_B12, data[26]);
        cv.put(COL_PM_VIT_C, data[27]);
        cv.put(COL_PM_VIT_E, data[28]);
        cv.put(COL_PM_VIT_K, data[29]);
        cv.put(COL_PM_VIT_H, data[30]);
        db.replace(TABLE_PM, null, cv);
    }

    public Cursor getConsumedMeals(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + TABLE_CM + "." + COL_CM_INDEX + ", " + COL_CM_AMOUNT + ", " + COL_PM_NAME + ", " + COL_PM_CALORIES +
                " FROM " + TABLE_CM +
                " INNER JOIN " + TABLE_PM + " ON " + TABLE_CM + "." + COL_CM_INDEX + " = " + TABLE_PM + "." + COL_PM_INDEX +
                " WHERE " + COL_CM_DATE + " = ?";
        return db.rawQuery(query, new String[]{date});
    }

    public void removeConsumedMeal(String date, String mealUUID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CM, COL_CM_DATE + " = ? AND " + COL_CM_INDEX + " = ?", new String[]{date, mealUUID});
    }

    // Workout methods -----------------------------------------------------------------------------

    public Cursor getWorkoutPlans() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_WP, null);
    }

    public void addWorkoutPlan(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_WP_NAME, name);
        db.insert(TABLE_WP, null, cv);
    }

    public void updateWorkoutPlanName(String oldName, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_WP_NAME, newName);
        db.update(TABLE_WP, cv, COL_WP_NAME + " = ?", new String[]{oldName});

        ContentValues cvRoutines = new ContentValues();
        cvRoutines.put(COL_WR_PLAN_NAME, newName);
        db.update(TABLE_WR, cvRoutines, COL_WR_PLAN_NAME + " = ?", new String[]{oldName});

        ContentValues cvExercises = new ContentValues();
        cvExercises.put(COL_WE_PLAN_NAME, newName);
        db.update(TABLE_WE, cvExercises, COL_WE_PLAN_NAME + " = ?", new String[]{oldName});
    }

    public void deleteWorkoutPlan(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WP, COL_WP_NAME + " = ?", new String[]{name});
        db.delete(TABLE_WR, COL_WR_PLAN_NAME + " = ?", new String[]{name});
        db.delete(TABLE_WE, COL_WE_PLAN_NAME + " = ?", new String[]{name});
    }

    public Cursor getWorkoutRoutines(String workoutPlanName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COL_WR_ROUTINE_NAME + " FROM " + TABLE_WR + " WHERE " + COL_WR_PLAN_NAME + " = ?", new String[]{workoutPlanName});
    }

    public void addWorkoutRoutine(String planName, String routineName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_WR_PLAN_NAME, planName);
        cv.put(COL_WR_ROUTINE_NAME, routineName);
        db.insert(TABLE_WR, null, cv);
    }

    public void updateWorkoutRoutineName(String planName, String oldRoutineName, String newRoutineName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_WR_ROUTINE_NAME, newRoutineName);
        db.update(TABLE_WR, cv, COL_WR_PLAN_NAME + " = ? AND " + COL_WR_ROUTINE_NAME + " = ?", new String[]{planName, oldRoutineName});

        ContentValues cvExercises = new ContentValues();
        cvExercises.put(COL_WE_ROUTINE_NAME, newRoutineName);
        db.update(TABLE_WE, cvExercises, COL_WE_PLAN_NAME + " = ? AND " + COL_WE_ROUTINE_NAME + " = ?", new String[]{planName, oldRoutineName});
    }

    public void deleteWorkoutRoutine(String planName, String routineName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WR, COL_WR_PLAN_NAME + " = ? AND " + COL_WR_ROUTINE_NAME + " = ?", new String[]{planName, routineName});
        db.delete(TABLE_WE, COL_WE_PLAN_NAME + " = ? AND " + COL_WE_ROUTINE_NAME + " = ?", new String[]{planName, routineName});
    }

    public Cursor getWorkoutExercises(String planName, String routineName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_WE + " WHERE " + COL_WE_PLAN_NAME + " = ? AND " + COL_WE_ROUTINE_NAME + " = ?", new String[]{planName, routineName});
    }

    public void addWorkoutExercise(String planName, String routineName, String exerciseName, int sets, int reps, double weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_WE_PLAN_NAME, planName);
        cv.put(COL_WE_ROUTINE_NAME, routineName);
        cv.put(COL_WE_EXERCISE_NAME, exerciseName);
        cv.put(COL_WE_SETS, sets);
        cv.put(COL_WE_REPETITIONS, reps);
        cv.put(COL_WE_WEIGHT, weight);
        db.insert(TABLE_WE, null, cv);
    }

    public void deleteWorkoutExercise(String planName, String routineName, String exerciseName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WE, COL_WE_PLAN_NAME + " = ? AND " + COL_WE_ROUTINE_NAME + " = ? AND " + COL_WE_EXERCISE_NAME + " = ?", new String[]{planName, routineName, exerciseName});
    }

    // Settings methods ----------------------------------------------------------------------------

    public Cursor getSettingsLanguage() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_S_LANG, null);
    }

    public void setSettingsLanguage(String lang) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_S_INDEX, 1);
        cv.put(COL_S_LANG, lang);
        db.replace(TABLE_S_LANG, null, cv);
    }

    public Cursor getSettingsGoals() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_S_GOAL, null);
    }

    public void setSettingsGoals(double cal, double fat, double carbs, double protein) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_S_INDEX, 1);
        cv.put(COL_S_GOAL_CALORIES, cal);
        cv.put(COL_S_GOAL_FAT, fat);
        cv.put(COL_S_GOAL_CARBS, carbs);
        cv.put(COL_S_GOAL_PROTEIN, protein);
        db.replace(TABLE_S_GOAL, null, cv);
    }

    // Recipe methods ------------------------------------------------------------------------------

    public String getRecipeContent(String title) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT " + COL_RECIPE_CONTENT + " FROM " + TABLE_RECIPES + " WHERE " + COL_RECIPE_TITLE + " = ?", new String[]{title});
            String content = null;
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    content = cursor.getString(0);
                }
                cursor.close();
            }
            return content;
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error getting recipe content", e);
            return null;
        }
    }

}
