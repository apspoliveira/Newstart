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
    private static final String DATABASE_NAME = "newstart.db";
    private static final int DATABASE_VERSION = 53;

    // Table body-data
    private static final String TABLE_BD = "body_data";
    private static final String COLUMN_BD_DATE = "date";
    private static final String COLUMN_BD_WEIGHT = "weight";
    private static final String COLUMN_BD_CHEST = "chest";
    private static final String COLUMN_BD_BELLY = "belly";
    private static final String COLUMN_BD_BUTT = "butt";
    private static final String COLUMN_BD_WAIST = "waist";
    private static final String COLUMN_BD_ARM_R = "arm_r";
    private static final String COLUMN_BD_ARM_L = "arm_l";
    private static final String COLUMN_BD_LEG_R = "leg_r";
    private static final String COLUMN_BD_LEG_L = "leg_l";

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
    private static final String COL_PM_CHOL = "chol";
    private static final String COL_PM_CREATINE = "creatine";
    private static final String COL_PM_CA = "ca";
    private static final String COL_PM_FE = "fe";
    private static final String COL_PM_K = "k";
    private static final String COL_PM_MG = "mg";
    private static final String COL_PM_MN = "mn";
    private static final String COL_PM_NA = "na";
    private static final String COL_PM_P = "p";
    private static final String COL_PM_ZN = "zn";
    private static final String COL_PM_VIT_A = "vit_a";
    private static final String COL_PM_VIT_B1 = "vit_b1";
    private static final String COL_PM_VIT_B2 = "vit_b2";
    private static final String COL_PM_VIT_B3 = "vit_b3";
    private static final String COL_PM_VIT_B5 = "vit_b5";
    private static final String COL_PM_VIT_B6 = "vit_b6";
    private static final String COL_PM_VIT_B7 = "vit_b7";
    private static final String COL_PM_VIT_B11 = "vit_b11";
    private static final String COL_PM_VIT_B12 = "vit_b12";
    private static final String COL_PM_VIT_C = "vit_c";
    private static final String COL_PM_VIT_E = "vit_e";
    private static final String COL_PM_VIT_K = "vit_k";
    private static final String COL_PM_VIT_H = "vit_h";

    private static final String TABLE_PMC = "meal_categories";
    private static final String COL_PMC_NAME = "name";

    // Table meals per day
    private static final String TABLE_CM = "consumed_meals";
    private static final String COL_CM_DATE = "date";
    private static final String COL_CM_INDEX = "meal_index";  // Refers to uuid-index of a food from foods-table
    private static final String COL_CM_AMOUNT = "amount";

    // Table exercises
    private static final String TABLE_WP = "workout_plans";
    private static final String COL_WP_NAME = "plan_name";

    private static final String TABLE_WR = "workout_routines";
    private static final String COL_WR_PLAN_NAME = "plan_name";
    private static final String COL_WR_ROUTINE_NAME = "routine_name";

    private static final String TABLE_WE = "workout_exercises";
    private static final String COL_WE_PLAN_NAME = "plan_name";
    private static final String COL_WE_ROUTINE_NAME = "routine_name";
    private static final String COL_WE_EXERCISE_NAME = "exercise_name";
    private static final String COL_WE_SETS = "sets";
    private static final String COL_WE_REPETITIONS = "reps";
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
                "PRIMARY KEY (" + COL_CM_DATE + ", " + COL_CM_INDEX + ", " + COL_CM_AMOUNT + "));"
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
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RECIPES + " VALUES('Oatmeal with Blueberries and Walnuts', '<h1>Oatmeal with Blueberries and Walnuts</h1><h3>Ingredients (1 Serving)</h3><ul><li><b>Oats:</b> 1/2 cup rolled oats or steel-cut.</li><li><b>Liquid:</b> 1 cup milk (dairy or non-dairy) or water.</li><li><b>Blueberries:</b> 1/2 cup fresh or frozen.</li><li><b>Walnuts:</b> 1/4 cup chopped.</li><li><b>Sweetener/Spices:</b> 1 tsp maple syrup or honey, 1/2 tsp ground cinnamon, and a pinch of salt.</li></ul><h3>Instructions</h3><ol><li><b>Boil:</b> In a small saucepan, bring the milk or water to a boil.</li><li><b>Cook:</b> Add the oats and a pinch of salt. Reduce heat to low, cover, and simmer for 5–7 minutes (or 20+ mins for steel-cut), stirring occasionally until creamy.</li><li><b>Mix-ins:</b> Remove from heat and stir in cinnamon and maple syrup.</li><li><b>Top & Serve:</b> Transfer to a bowl and top with blueberries and walnuts.</li></ol>');");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RECIPES + " VALUES('Quinoa Salad with Roasted Vegetables', '<h1>Quinoa Salad with Roasted Vegetables</h1><h3>Ingredients</h3><ul><li><b>Quinoa:</b> 1 cup uncooked (rinsed), cooked in 2 cups water or vegetable broth.</li><li><b>Vegetables:</b> 1 diced red bell pepper, 1 diced zucchini, 1/2 red onion (cubed), 1 cup cherry tomatoes, or 1 cup broccoli florets.</li><li><b>Dressing:</b> 3-4 tbsp olive oil, 1-2 tbsp lemon juice or balsamic vinegar, 1 tsp dried herbs (origano or thyme), 1 minced garlic clove, salt, and pepper.</li><li><b>Optional Toppings:</b> 1/2 cup crumbled feta or goat cheese, toasted pine nuts, fresh parsley or cilantro.</li></ul><h3>Instructions</h3><ol><li><b>Roast Veggies:</b> Preheat oven to 400°F–425°F (200°C–220°C). Toss diced vegetables with olive oil, salt, pepper, and dried herbs on a baking sheet. Roast for 20-30 minutes until tender and slightly browned.</li><li><b>Cook Quinoa:</b> Rinse quinoa, then combine with 2 cups water/broth in a saucepan. Bring to a boil, reduce to a simmer, cover, and cook for 15 minutes, or until water is absorbed. Fluff with a fork.</li><li><b>Prepare Dressing:</b> Whisk olive oil, lemon juice/balsamic, garlic, and herbs together.</li><li><b>Assemble:</b> Combine quinoa and roasted vegetables in a large bowl. Drizzle with dressing and toss to combine. Top with feta and fresh herbs.</li></ol>');");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RECIPES + " VALUES('Steared Broccoli and Baked Tofu', '<h1>Steamed Broccoli and Baked Tofu</h1><h3>Ingredients</h3><ul><li><b>Tofu:</b> 1 block (14-16 oz) extra-firm tofu, pressed and cubed.</li><li><b>Broccoli:</b> 1 large head, cut into florets.</li><li><b>Coating/Oil:</b> 2 tbsp olive oil or sesame oil, 1-2 tbsp cornstarch (for crispiness).</li><li><b>Flavorings:</b> 2-3 tbsp soy sauce or tamari, 2 cloves garlic (minced), 1 tsp garlic powder, salt, and pepper to taste.</li><li><b>Optional Sauce:</b> 1 tbsp maple syrup or honey, 1 tbsp rice vinegar, 1 tsp ginger.</li></ul><h3>Instructions</h3><ol><li><b>Prep the Tofu:</b> Preheat oven to 400°F (200°C). Press tofu for 15-30 minutes to remove excess water, then cube.</li><li><b>Coat Tofu:</b> Toss cubed tofu with cornstarch, 1 tbsp soy sauce, and 1 tbsp oil until evenly coated.</li><li><b>Arrange and Bake:</b> Spread tofu on a parchment-lined baking sheet. Bake for 15-20 minutes.</li><li><b>Prepare Broccoli:</b> While tofu bakes, toss broccoli florets with 1 tbsp oil, garlic, and salt.</li><li><b>Roast Together:</b> Remove baking sheet, move tofu to one side, and add broccoli to the other, or mix together. Bake for another 10-15 minutes until tofu is golden and broccoli is tender.</li><li><b>Serve:</b> Toss with additional soy sauce or a quick sauce (maple syrup/vinegar) if desired, and garnish with sesame seeds.</li></ol><h3>Quick Tips</h3><ul><li>Pressing the tofu is key for a better texture!</li></ul>');");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RECIPES + " VALUES('Whole Grain Pancakes with Fresh Fruit', '<h1>Whole Grain Pancakes with Fresh Fruit</h1><h3>Ingredients</h3><ul><li><b>Dry:</b> 1 cup whole wheat flour (or white whole wheat), 2 tsp baking powder, 1/2 tsp salt, 1/2 tsp cinnamon (optional).</li><li><b>Wet:</b> 1 cup milk (dairy or almond), 1 large egg, 2 tbsp melted butter or oil, 1 tsp vanilla extract, 1 tbsp honey or maple syrup (optional).</li><li><b>Fruit/Toppings:</b> 1 cup fresh blueberries, sliced bananas, or diced strawberries.</li></ul><h3>Instructions</h3><ol><li><b>Mix Dry Ingredients:</b> In a large bowl, whisk together the whole wheat flour, baking powder, salt, and cinnamon.</li><li><b>Combine Wet Ingredients:</b> In a separate bowl, whisk the egg, milk, oil/butter, honey, and vanilla.</li><li><b>Create Batter:</b> Pour the wet ingredients into the dry ingredients and stir gently until just combined. The batter should be slightly lumpy; overmixing makes them tough.</li><li><b>Heat Pan:</b> Preheat a griddle or non-stick skillet over medium heat and lightly grease with oil or butter.</li><li><b>Cook:</b> Pour 1/4 cup of batter for each pancake. If adding fruit into the batter, add them now, or place them on top of the pancake immediately after pouring.</li><li><b>Flip:</b> Cook for 2–4 minutes until bubbles form on the surface and the edges look dry, then flip and cook for another 1–2 minutes until golden brown.</li><li><b>Serve:</b> Serve warm with fresh fruit on top, maple syrup, or yogurt.</li></ol>');");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RECIPES + " VALUES('Black Bean and Corn Tacos', '<h1>Black Bean and Corn Tacos</h1><h3>Ingredients</h3><ul><li><b>Beans/Corn:</b> 1 can (15 oz) black beans (rinsed/drained), 1 cup corn (frozen or fresh).</li><li><b>Aromatics/Fat:</b> 1 tbsp olive oil, 1/2 diced onion, 1-2 cloves minced garlic.</li><li><b>Spices:</b> 1 tsp cumin, 1 tsp chili powder, 1/2 tsp smoked paprika (optional), salt to taste.</li><li><b>Flavor Boosters:</b> 1 tbsp lime juice, 1/4 cup salsa, fresh cilantro.</li><li><b>Tortillas/Toppings:</b> 8 corn or flour tortillas, shredded cheese, sour cream, avocado/guacamole.</li></ul><h3>Instructions</h3><ol><li><b>Sauté Aromatics:</b> Heat olive oil in a skillet over medium heat. Add onion and cook until soft (5-8 mins). Add garlic and spices (cumin/chili powder), cooking for another 30-60 seconds until fragrant.</li><li><b>Cook Filling:</b> Add the black beans and corn to the skillet. Stir in salsa and lime juice, cooking until heated through (3-5 mins).</li><li><b>Mash (Optional):</b> Use a fork or potato masher to mash about half of the beans—this creates a creamier, cohesive texture that stays in the taco better.</li><li><b>Warm Tortillas:</b> Warm tortillas in a dry skillet, over a gas flame, or wrapped in a damp paper towel in the microwave.</li><li><b>Assemble:</b> Spoon the bean mixture into tortillas. Top with cheese, cilantro, and other desired toppings.</li></ol>');");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RECIPES + " VALUES('Lentil Soup with Kale', '<h1>Lentil Soup with Kale</h1><h3>Ingredients</h3><ul><li>1 tbsp Olive oil</li><li>1 Large onion, diced</li><li>2-3 Garlic cloves, minced</li><li>2-3 Carrots, diced</li><li>2 Celery stalks, diced</li><li>1 cup Brown or Green lentils, rinsed</li><li>4-6 cups Vegetable broth</li><li>1 (14 oz) Can diced tomatoes</li><li>1 tsp Cumin</li><li>1 tsp Dried thyme or Italian seasoning</li><li>1 bunch Kale, stemmed and chopped</li><li>1-2 tsp Lemon juice (optional, for brightness)</li><li>Salt and pepper to taste</li></ul><h3>Instructions</h3><ol><li><b>Sauté Aromatics:</b> Heat olive oil in a large pot or Dutch oven over medium heat. Add onion, carrot, and celery. Sauté for 5-7 minutes until soft. Add garlic and spices (cumin, thyme) and cook for 1 minute until fragrant.</li><li><b>Simmer Soup:</b> Add the rinsed lentils, vegetable broth, and diced tomatoes (with juices). Bring to a boil, then reduce heat to low, cover partially, and simmer for 25-30 minutes, or until the lentils are tender.</li><li><b>Add Kale:</b> Stir in the chopped kale and cook for 3-5 minutes until just wilted.</li><li><b>Finish and Serve:</b> Remove from heat and stir in the lemon juice, if using. Season with additional salt and pepper to taste.</li></ol>');");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RECIPES + " VALUES('Millet with Dates and Cashews', '<h1>Millet with Dates and Cashews</h1><h3>Ingredients</h3><ul><li><b>Millet:</b> 1/2 cup hulled millet.</li><li><b>Liquid:</b> 1.5 cups water or almond milk.</li><li><b>Dates:</b> 4-5 Medjool dates, pitted and chopped.</li><li><b>Cashews:</b> 1/4 cup raw or roasted cashews.</li><li><b>Spices:</b> 1/4 tsp cardamom or cinnamon.</li></ul><h3>Instructions</h3><ol><li><b>Toast Millet:</b> In a small pot, toast millet over medium heat for 2-3 mins until fragrant.</li><li><b>Cook:</b> Add liquid and bring to a boil. Reduce heat to low, cover, and simmer for 15-20 mins until liquid is absorbed.</li><li><b>Combine:</b> Stir in chopped dates and cashews. Let sit covered for 5 mins.</li><li><b>Serve:</b> Fluff with a fork and serve warm.</li></ol>');");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RECIPES + " VALUES('Quinoa and Black Bean Bowl', '<h1>Quinoa and Black Bean Bowl</h1><h3>Ingredients</h3><ul><li>1 cup quinoa, rinsed</li><li>2 cups water or vegetable broth</li><li>1 can (15 oz) black beans, rinsed and drained</li><li>1 cup corn (fresh, frozen, or canned)</li><li>1 red bell pepper, diced</li><li>1/4 cup red onion, finely chopped</li><li>1/2 cup fresh cilantro, chopped</li><li>1 avocado, sliced</li></ul><h3>Dressing</h3><ul><li>2 tbsp olive oil</li><li>1 tbsp lime juice</li><li>1 tsp cumin</li><li>Salt and pepper to taste</li></ul><h3>Instructions</h3><ol><li>Cook quinoa in water or broth according to package instructions.</li><li>In a large bowl, combine cooked quinoa, black beans, corn, bell pepper, and onion.</li><li>Whisk together dressing ingredients and pour over the salad.</li><li>Toss to combine. Top with cilantro and avocado before serving.</li></ol>');");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RECIPES + " VALUES('Baked Sweet Potato with Greens', '<h1>Baked Sweet Potato with Greens</h1><h3>Ingredients</h3><ul><li>2 large sweet potatoes</li><li>2 cups kale or spinach, chopped</li><li>1/2 cup chickpeas, cooked</li><li>1 tbsp olive oil</li><li>1 clove garlic, minced</li><li>Lemon juice for drizzling</li><li>Salt and pepper to taste</li></ul><h3>Instructions</h3><ol><li>Preheat oven to 400°F (200°C). Pierce sweet potatoes with a fork and bake for 45-60 minutes until tender.</li><li>In a skillet, heat olive oil over medium heat. Add garlic and sauté for 1 minute.</li><li>Add greens and chickpeas. Sauté until greens are wilted.</li><li>Cut open baked sweet potatoes and stuff with the greens and chickpea mixture.</li><li>Drizzle with lemon juice and season with salt and pepper.</li></ol>');");

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
        if (oldVersion < 51) {
            // Ensure all tables exist
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_RECIPES + " ("
                    + COL_RECIPE_TITLE + " TEXT PRIMARY KEY, "
                    + COL_RECIPE_CONTENT + " TEXT);");

            db.execSQL("INSERT OR IGNORE INTO " + TABLE_RECIPES + " VALUES('Garden Lentil Stew', '<h1>Garden Lentil Stew</h1><p>A hearty and nutritious stew packed with lentils and fresh vegetables.</p><h3>Ingredients</h3><ul><li>1 cup brown lentils</li><li>1 onion, chopped</li><li>2 carrots, sliced</li><li>2 stalks celery, sliced</li><li>3 cloves garlic, minced</li><li>1 can diced tomatoes</li><li>4 cups vegetable broth</li><li>1 tsp thyme</li><li>1 tsp oregano</li><li>Salt and pepper to taste</li><li>2 cups spinach</li></ul><h3>Instructions</h3><ol><li>In a large pot, sauté onion, carrots, and celery until softened.</li><li>Add garlic and cook for 1 minute.</li><li>Stir in lentils, tomatoes, broth, thyme, and oregano.</li><li>Bring to a boil, then reduce heat and simmer for 30-40 minutes until lentils are tender.</li><li>Stir in spinach and cook until wilted.</li><li>Season with salt and pepper.</li></ol>');");
            db.execSQL("INSERT OR IGNORE INTO " + TABLE_RECIPES + " VALUES('Oatmeal with Blueberries and Walnuts', '<h1>Oatmeal with Blueberries and Walnuts</h1><h3>Ingredients (1 Serving)</h3><ul><li><b>Oats:</b> 1/2 cup rolled oats or steel-cut.</li><li><b>Liquid:</b> 1 cup milk (dairy or non-dairy) or water.</li><li><b>Blueberries:</b> 1/2 cup fresh or frozen.</li><li><b>Walnuts:</b> 1/4 cup chopped.</li><li><b>Sweetener/Spices:</b> 1 tsp maple syrup or honey, 1/2 tsp ground cinnamon, and a pinch of salt.</li></ul><h3>Instructions</h3><ol><li><b>Boil:</b> In a small saucepan, bring the milk or water to a boil.</li><li><b>Cook:</b> Add the oats and a pinch of salt. Reduce heat to low, cover, and simmer for 5–7 minutes (or 20+ mins for steel-cut), stirring occasionally until creamy.</li><li><b>Mix-ins:</b> Remove from heat and stir in cinnamon and maple syrup.</li><li><b>Top & Serve:</b> Transfer to a bowl and top with blueberries and walnuts.</li></ol>');");
            db.execSQL("INSERT OR IGNORE INTO " + TABLE_RECIPES + " VALUES('Quinoa Salad with Roasted Vegetables', '<h1>Quinoa Salad with Roasted Vegetables</h1><h3>Ingredients</h3><ul><li><b>Quinoa:</b> 1 cup uncooked (rinsed), cooked in 2 cups water or vegetable broth.</li><li><b>Vegetables:</b> 1 diced red bell pepper, 1 diced zucchini, 1/2 red onion (cubed), 1 cup cherry tomatoes, or 1 cup broccoli florets.</li><li><b>Dressing:</b> 3-4 tbsp olive oil, 1-2 tbsp lemon juice or balsamic vinegar, 1 tsp dried herbs (oregano or thyme), 1 minced garlic clove, salt, and pepper.</li><li><b>Optional Toppings:</b> 1/2 cup crumbled feta or goat cheese, toasted pine nuts, fresh parsley or cilantro.</li></ul><h3>Instructions</h3><ol><li><b>Roast Veggies:</b> Preheat oven to 400°F–425°F (200°C–220°C). Toss diced vegetables with olive oil, salt, pepper, and dried herbs on a baking sheet. Roast for 20-30 minutes until tender and slightly browned.</li><li><b>Cook Quinoa:</b> Rinse quinoa, then combine with 2 cups water/broth in a saucepan. Bring to a boil, reduce to a simmer, cover, and cook for 15 minutes, or until water is absorbed. Fluff with a fork.</li><li><b>Prepare Dressing:</b> Whisk olive oil, lemon juice/balsamic, garlic, and herbs together.</li><li><b>Assemble:</b> Combine quinoa and roasted vegetables in a large bowl. Drizzle with dressing and toss to combine. Top with feta and fresh herbs.</li></ol>');");
            db.execSQL("INSERT OR IGNORE INTO " + TABLE_RECIPES + " VALUES('Steared Broccoli and Baked Tofu', '<h1>Steamed Broccoli and Baked Tofu</h1><h3>Ingredients</h3><ul><li><b>Tofu:</b> 1 block (14-16 oz) extra-firm tofu, pressed and cubed.</li><li><b>Broccoli:</b> 1 large head, cut into florets.</li><li><b>Coating/Oil:</b> 2 tbsp olive oil or sesame oil, 1-2 tbsp cornstarch (for crispiness).</li><li><b>Flavorings:</b> 2-3 tbsp soy sauce or tamari, 2 cloves garlic (minced), 1 tsp garlic powder, salt, and pepper to taste.</li><li><b>Optional Sauce:</b> 1 tbsp maple syrup or honey, 1 tbsp rice vinegar, 1 tsp ginger.</li></ul><h3>Instructions</h3><ol><li><b>Prep the Tofu:</b> Preheat oven to 400°F (200°C). Press tofu for 15-30 minutes to remove excess water, then cube.</li><li><b>Coat Tofu:</b> Toss cubed tofu with cornstarch, 1 tbsp soy sauce, and 1 tbsp oil until evenly coated.</li><li><b>Arrange and Bake:</b> Spread tofu on a parchment-lined baking sheet. Bake for 15-20 minutes.</li><li><b>Prepare Broccoli:</b> While tofu bakes, toss broccoli florets with 1 tbsp oil, garlic, and salt.</li><li><b>Roast Together:</b> Remove baking sheet, move tofu to one side, and add broccoli to the other, or mix together. Bake for another 10-15 minutes until tofu is golden and broccoli is tender.</li><li><b>Serve:</b> Toss with additional soy sauce or a quick sauce (maple syrup/vinegar) if desired, and garnish with sesame seeds.</li></ol><h3>Quick Tips</h3><ul><li>Pressing the tofu is key for a better texture!</li></ul>');");
            db.execSQL("INSERT OR IGNORE INTO " + TABLE_RECIPES + " VALUES('Whole Grain Pancakes with Fresh Fruit', '<h1>Whole Grain Pancakes with Fresh Fruit</h1><h3>Ingredients</h3><ul><li><b>Dry:</b> 1 cup whole wheat flour (or white whole wheat), 2 tsp baking powder, 1/2 tsp salt, 1/2 tsp cinnamon (optional).</li><li><b>Wet:</b> 1 cup milk (dairy or almond), 1 large egg, 2 tbsp melted butter or oil, 1 tsp vanilla extract, 1 tbsp honey or maple syrup (optional).</li><li><b>Fruit/Toppings:</b> 1 cup fresh blueberries, sliced bananas, or diced strawberries.</li></ul><h3>Instructions</h3><ol><li><b>Mix Dry Ingredients:</b> In a large bowl, whisk together the whole wheat flour, baking powder, salt, and cinnamon.</li><li><b>Combine Wet Ingredients:</b> In a separate bowl, whisk the egg, milk, oil/butter, honey, and vanilla.</li><li><b>Create Batter:</b> Pour the wet ingredients into the dry ingredients and stir gently until just combined. The batter should be slightly lumpy; overmixing makes them tough.</li><li><b>Heat Pan:</b> Preheat a griddle or non-stick skillet over medium heat and lightly grease with oil or butter.</li><li><b>Cook:</b> Pour 1/4 cup of batter for each pancake. If adding fruit into the batter, add them now, or place them on top of the pancake immediately after pouring.</li><li><b>Flip:</b> Cook for 2–4 minutes until bubbles form on the surface and the edges look dry, then flip and cook for another 1–2 minutes until golden brown.</li><li><b>Serve:</b> Serve warm with fresh fruit on top, maple syrup, or yogurt.</li></ol>');");
            db.execSQL("INSERT OR IGNORE INTO " + TABLE_RECIPES + " VALUES('Black Bean and Corn Tacos', '<h1>Black Bean and Corn Tacos</h1><h3>Ingredients</h3><ul><li><b>Beans/Corn:</b> 1 can (15 oz) black beans (rinsed/drained), 1 cup corn (frozen or fresh).</li><li><b>Aromatics/Fat:</b> 1 tbsp olive oil, 1/2 diced onion, 1-2 cloves minced garlic.</li><li><b>Spices:</b> 1 tsp cumin, 1 tsp chili powder, 1/2 tsp smoked paprika (optional), salt to taste.</li><li><b>Flavor Boosters:</b> 1 tbsp lime juice, 1/4 cup salsa, fresh cilantro.</li><li><b>Tortillas/Toppings:</b> 8 corn or flour tortillas, shredded cheese, sour cream, avocado/guacamole.</li></ul><h3>Instructions</h3><ol><li><b>Sauté Aromatics:</b> Heat olive oil in a skillet over medium heat. Add onion and cook until soft (5-8 mins). Add garlic and spices (cumin/chili powder), cooking for another 30-60 seconds until fragrant.</li><li><b>Cook Filling:</b> Add the black beans and corn to the skillet. Stir in salsa and lime juice, cooking until heated through (3-5 mins).</li><li><b>Mash (Optional):</b> Use a fork or potato masher to mash about half of the beans—this creates a creamier, cohesive texture that stays in the taco better.</li><li><b>Warm Tortillas:</b> Warm tortillas in a dry skillet, over a gas flame, or wrapped in a damp paper towel in the microwave.</li><li><b>Assemble:</b> Spoon the bean mixture into tortillas. Top with cheese, cilantro, and other desired toppings.</li></ol>');");
            db.execSQL("INSERT OR IGNORE INTO " + TABLE_RECIPES + " VALUES('Lentil Soup with Kale', '<h1>Lentil Soup with Kale</h1><h3>Ingredients</h3><ul><li>1 tbsp Olive oil</li><li>1 Large onion, diced</li><li>2-3 Garlic cloves, minced</li><li>2-3 Carrots, diced</li><li>2 Celery stalks, diced</li><li>1 cup Brown or Green lentils, rinsed</li><li>4-6 cups Vegetable broth</li><li>1 (14 oz) Can diced tomatoes</li><li>1 tsp Cumin</li><li>1 tsp Dried thyme or Italian seasoning</li><li>1 bunch Kale, stemmed and chopped</li><li>1-2 tsp Lemon juice (optional, for brightness)</li><li>Salt and pepper to taste</li></ul><h3>Instructions</h3><ol><li><b>Sauté Aromatics:</b> Heat olive oil in a large pot or Dutch oven over medium heat. Add onion, carrot, and celery. Sauté for 5-7 minutes until soft. Add garlic and spices (cumin, thyme) and cook for 1 minute until fragrant.</li><li><b>Simmer Soup:</b> Add the rinsed lentils, vegetable broth, and diced tomatoes (with juices). Bring to a boil, then reduce heat to low, cover partially, and simmer for 25-30 minutes, or until the lentils are tender.</li><li><b>Add Kale:</b> Stir in the chopped kale and cook for 3-5 minutes until just wilted.</li><li><b>Finish and Serve:</b> Remove from heat and stir in the lemon juice, if using. Season with additional salt and pepper to taste.</li></ol>');");
            db.execSQL("INSERT OR IGNORE INTO " + TABLE_RECIPES + " VALUES('Millet with Dates and Cashews', '<h1>Millet with Dates and Cashews</h1><h3>Ingredients</h3><ul><li><b>Millet:</b> 1/2 cup hulled millet.</li><li><b>Liquid:</b> 1.5 cups water or almond milk.</li><li><b>Dates:</b> 4-5 Medjool dates, pitted and chopped.</li><li><b>Cashews:</b> 1/4 cup raw or roasted cashews.</li><li><b>Spices:</b> 1/4 tsp cardamom or cinnamon.</li></ul><h3>Instructions</h3><ol><li><b>Toast Millet:</b> In a small pot, toast millet over medium heat for 2-3 mins until fragrant.</li><li><b>Cook:</b> Add liquid and bring to a boil. Reduce heat to low, cover, and simmer for 15-20 mins until liquid is absorbed.</li><li><b>Combine:</b> Stir in chopped dates and cashews. Let sit covered for 5 mins.</li><li><b>Serve:</b> Fluff with a fork and serve warm.</li></ol>');");
            db.execSQL("INSERT OR IGNORE INTO " + TABLE_RECIPES + " VALUES('Quinoa and Black Bean Bowl', '<h1>Quinoa and Black Bean Bowl</h1><h3>Ingredients</h3><ul><li>1 cup quinoa, rinsed</li><li>2 cups water or vegetable broth</li><li>1 can (15 oz) black beans, rinsed and drained</li><li>1 cup corn (fresh, frozen, or canned)</li><li>1 red bell pepper, diced</li><li>1/4 cup red onion, finely chopped</li><li>1/2 cup fresh cilantro, chopped</li><li>1 avocado, sliced</li></ul><h3>Dressing</h3><ul><li>2 tbsp olive oil</li><li>1 tbsp lime juice</li><li>1 tsp cumin</li><li>Salt and pepper to taste</li></ul><h3>Instructions</h3><ol><li>Cook quinoa in water or broth according to package instructions.</li><li>In a large bowl, combine cooked quinoa, black beans, corn, bell pepper, and onion.</li><li>Whisk together dressing ingredients and pour over the salad.</li><li>Toss to combine. Top with cilantro and avocado before serving.</li></ol>');");
            db.execSQL("INSERT OR IGNORE INTO " + TABLE_RECIPES + " VALUES('Baked Sweet Potato with Greens', '<h1>Baked Sweet Potato with Greens</h1><h3>Ingredients</h3><ul><li>2 large sweet potatoes</li><li>2 cups kale or spinach, chopped</li><li>1/2 cup chickpeas, cooked</li><li>1 tbsp olive oil</li><li>1 clove garlic, minced</li><li>Lemon juice for drizzling</li><li>Salt and pepper to taste</li></ul><h3>Instructions</h3><ol><li>Preheat oven to 400°F (200°C). Pierce sweet potatoes with a fork and bake for 45-60 minutes until tender.</li><li>In a skillet, heat olive oil over medium heat. Add garlic and sauté for 1 minute.</li><li>Add greens and chickpeas. Sauté until greens are wilted.</li><li>Cut open baked sweet potatoes and stuff with the greens and chickpea mixture.</li><li>Drizzle with lemon juice and season with salt and pepper.</li></ol>');");

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
            db.execSQL("INSERT OR IGNORE INTO " + TABLE_WP + " VALUES('Example Workout Plan', 'PULL DAY');");
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
        String query = "SELECT " + TABLE_CM + "." + COL_CM_INDEX + ", " + COL_CM_AMOUNT + ", " + TABLE_PM + "." + COL_PM_NAME + ", " + TABLE_PM + "." + COL_PM_CALORIES +
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
