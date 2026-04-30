package newstart.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "newstart.db";
    private static final int DATABASE_VERSION = 76;

    // Table settings
    private static final String TABLE_S_GOAL = "settings_goals";
    private static final String COL_S_INDEX = "settings_index";
    private static final String COL_S_GOAL_CALORIES = "goal_calories";
    private static final String COL_S_GOAL_FAT = "goal_fat";
    private static final String COL_S_GOAL_CARBS = "goal_carbs";
    private static final String COL_S_GOAL_PROTEIN = "goal_protein";

    private static final String TABLE_S_LANG = "settings_lang";
    private static final String COL_S_INDEX_L = "settings_index";

    // Table recipes/articles
    private static final String TABLE_RECIPES = "recipes";
    private static final String COL_RECIPE_TITLE = "title";
    private static final String COL_RECIPE_CONTENT = "content";

    // Schema constants
    private static final String TABLE_BD = "body_data";
    private static final String TABLE_PM = "preset_meals";
    private static final String TABLE_PMC = "meal_categories";
    private static final String TABLE_CM = "consumed_meals";
    private static final String TABLE_WE = "workout_exercises";
    private static final String TABLE_WP = "workout_plans";
    private static final String TABLE_WR = "workout_routines";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_BD + " (date TEXT PRIMARY KEY, weight REAL, chest REAL, belly REAL, butt REAL, waist REAL, arm_r REAL, arm_l REAL, leg_r REAL, leg_l REAL);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PM + " (meal_index TEXT PRIMARY KEY, name TEXT, category TEXT, calories REAL, fat REAL, fat_sat REAL, carbs REAL, sugar REAL, protein REAL, salt REAL, fiber REAL, chol REAL, creatine REAL, ca REAL, fe REAL, k REAL, mg REAL, mn REAL, na REAL, p REAL, zn REAL, vit_a REAL, vit_b1 REAL, vit_b2 REAL, vit_b3 REAL, vit_b5 REAL, vit_b6 REAL, vit_b7 REAL, vit_b11 REAL, vit_b12 REAL, vit_c REAL, vit_e REAL, vit_k REAL, vit_h REAL);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PMC + " (name TEXT PRIMARY KEY);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CM + " (date TEXT, meal_index TEXT, amount REAL, PRIMARY KEY (date, meal_index, amount));");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_WE + " (plan_name TEXT, routine_name TEXT, exercise_name TEXT, sets INTEGER, reps INTEGER, weight REAL);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_WP + " (plan_name TEXT PRIMARY KEY);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_WR + " (plan_name TEXT, routine_name TEXT, PRIMARY KEY (plan_name, routine_name));");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_S_GOAL + " (" + COL_S_INDEX + " INTEGER PRIMARY KEY, " + COL_S_GOAL_CALORIES + " REAL, " + COL_S_GOAL_FAT + " REAL, " + COL_S_GOAL_CARBS + " REAL, " + COL_S_GOAL_PROTEIN + " REAL);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_S_LANG + " (" + COL_S_INDEX_L + " INTEGER PRIMARY KEY, language TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_RECIPES + " (" + COL_RECIPE_TITLE + " TEXT PRIMARY KEY, " + COL_RECIPE_CONTENT + " TEXT);");

        sqLiteDatabase.execSQL("INSERT OR IGNORE INTO " + TABLE_PMC + " VALUES('Fruits and Vegetables')");
        sqLiteDatabase.execSQL("INSERT OR IGNORE INTO " + TABLE_PMC + " VALUES('Meat and Fish')");
        sqLiteDatabase.execSQL("INSERT OR IGNORE INTO " + TABLE_PMC + " VALUES('Dairy and Eggs')");
        sqLiteDatabase.execSQL("INSERT OR IGNORE INTO " + TABLE_PMC + " VALUES('Bakery and Grains')");
        sqLiteDatabase.execSQL("INSERT OR IGNORE INTO " + TABLE_PMC + " VALUES('Drinks')");
        sqLiteDatabase.execSQL("INSERT OR IGNORE INTO " + TABLE_PMC + " VALUES('Others')");

        insertInitialRecipes(sqLiteDatabase);

        sqLiteDatabase.execSQL("INSERT OR IGNORE INTO " + TABLE_S_LANG + " VALUES(1, 'en');");
        sqLiteDatabase.execSQL("INSERT OR IGNORE INTO " + TABLE_S_GOAL + " VALUES(1, 2000, 65, 250, 50);");
    }

    private void insertInitialRecipes(SQLiteDatabase db) {
        // EN Recipes - Breakfasts
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Oatmeal with Blueberries and Walnuts', '<h1>Oatmeal with Blueberries and Walnuts</h1><h3>Ingredients</h3><ul><li>1/2 cup rolled oats</li><li>1 cup plant milk</li><li>1/2 cup blueberries</li><li>1/4 cup walnuts</li></ul><h3>Instructions</h3><p>Cook oats with milk for 5-7 mins. Top with blueberries and walnuts.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Whole Grain Pancakes with Fresh Fruit', '<h1>Whole Grain Pancakes with Fresh Fruit</h1><h3>Ingredients</h3><ul><li>1 cup whole wheat flour</li><li>1 tbsp baking powder</li><li>1 cup almond milk</li><li>Fresh fruit</li></ul><h3>Instructions</h3><p>Mix ingredients and cook portions on a non-stick pan. Serve with fruit.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Fruit Smoothie with Flax Seeds', '<h1>Fruit Smoothie</h1><h3>Ingredients</h3><ul><li>1 banana</li><li>1 cup frozen berries</li><li>1 tbsp flax seeds</li><li>1 cup plant milk</li></ul><h3>Instructions</h3><p>Blend everything until smooth.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Buckwheat Porridge with Almonds', '<h1>Buckwheat Porridge</h1><h3>Ingredients</h3><ul><li>1/2 cup buckwheat</li><li>1.5 cups water</li><li>Almonds</li></ul><h3>Instructions</h3><p>Simmer buckwheat for 15-20 mins. Serve with almonds.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Chia Pudding with Mango', '<h1>Chia Pudding</h1><h3>Ingredients</h3><ul><li>3 tbsp chia seeds</li><li>1 cup coconut milk</li><li>Mango</li></ul><h3>Instructions</h3><p>Mix chia and milk. Chill for 4 hours. Top with mango.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Whole Wheat Toast with Avocado', '<h1>Avocado Toast</h1><h3>Ingredients</h3><ul><li>2 slices whole wheat bread</li><li>1 avocado</li></ul><h3>Instructions</h3><p>Toast bread and spread mashed avocado on top.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Whole Grain Toast with Hummus', '<h1>Hummus Toast</h1><h3>Ingredients</h3><ul><li>2 slices whole grain bread</li><li>1/2 cup hummus</li><li>Cucumber</li></ul><h3>Instructions</h3><p>Toast bread. Spread hummus and top with cucumber slices.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Millet with Dates and Cashews', '<h1>Millet</h1><h3>Ingredients</h3><ul><li>1/2 cup millet</li><li>1.5 cups plant milk</li><li>Dates, cashews</li></ul><h3>Instructions</h3><p>Cook millet in milk for 20 mins. Mix in dates and cashews.</p>');");
        
        String tofuScrambleEn = "<h1>Tofu Scramble with Vegetables</h1><h3>Ingredients (serves 2):</h3><ul><li>1 block of firm tofu (approx. 250g)</li><li>1 tbsp olive oil</li><li>Various vegetables: onion, garlic, spinach, mushrooms</li><li>1/2 tsp turmeric powder, salt, pepper</li></ul><h3>Instructions:</h3><ol><li>Crumble tofu.</li><li>Sauté veggies.</li><li>Add tofu and spices, cook 5 mins.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tofu Scramble with Spinach', '" + tofuScrambleEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tofu and Veggie Hash', '" + tofuScrambleEn + "');");
        
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Acai Bowl with Homemade Granola', '<h1>Acai Bowl</h1><h3>Ingredients:</h3><ul><li>Acai pulp</li><li>1 banana</li><li>1/2 cup granola</li></ul><h3>Instructions:</h3><p>Blend acai and banana. Top with granola.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Breakfast Burrito (Beans/Veg)', '<h1>Breakfast Burrito</h1><h3>Ingredients:</h3><ul><li>1 whole wheat tortilla</li><li>1/2 cup black beans</li><li>Bell peppers</li></ul><h3>Instructions:</h3><p>Sauté peppers. Wrap with beans in a warm tortilla.</p>');");

        String quinoaSaladEn = "<h1>Quinoa Salad with Roasted Vegetables</h1><h3>Ingredients:</h3><ul><li>1 cup cooked quinoa</li><li>1 small eggplant, 1 zucchini, 1 red bell pepper</li><li>Olive oil, salt, pepper, herbs</li><li>Juice of 1/2 lemon</li></ul><h3>Preparation:</h3><ol><li>Roast veggies at 200°C for 25 min.</li><li>Mix with quinoa and lemon juice.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Quinoa Salad with Roasted Vegetables', '" + quinoaSaladEn + "');");

        String tacosEn = "<h1>Black Bean and Corn Tacos</h1><h3>Ingredients:</h3><ul><li>4 corn tortillas</li><li>1 can black beans</li><li>1 cup sweet corn</li><li>Avocado, cilantro, lime</li><li>Cmin, paprika, salt</li></ul><h3>Preparation:</h3><ol><li>Warm beans and corn with spices.</li><li>Fill tortillas and top with avocado.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Black Bean and Corn Tacos', '" + tacosEn + "');");

        String curryEn = "<h1>Chickpea Curry with Brown Rice</h1><h3>Ingredients:</h3><ul><li>2 cups cooked chickpeas</li><li>1 can coconut milk</li><li>1 tbsp curry powder</li><li>Cooked brown rice</li><li>Onion and garlic</li></ul><h3>Preparation:</h3><ol><li>Sauté onion and garlic, add curry powder.</li><li>Simmer with chickpeas and coconut milk for 10-15 min.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Chickpea Curry with Brown Rice', '" + curryEn + "');");

        String stewEn = "<h1>Lentil and Vegetable Stew</h1><h3>Ingredients:</h3><ul><li>1 cup dried lentils</li><li>2 carrots, 2 potatoes, 1 onion</li><li>800ml vegetable broth</li><li>Bay leaf and olive oil</li></ul><h3>Preparation:</h3><ol><li>Base: Sauté onion and garlic. Add diced carrots and potatoes.</li><li>Cook: Add lentils, bay leaf, and broth.</li><li>Time: Cover and simmer for 25-30 min until lentils are tender.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Lentil and Vegetable Stew', '" + stewEn + "');");

        String tabouliEn = "<h1>Tabouli with Extra Parsley</h1><h3>Ingredients:</h3><ul><li>Bulgur wheat, fresh parsley, mint, tomatoes, cucumber</li><li>Olive oil, lemon juice</li></ul><h3>Preparation:</h3><p>Hydrate bulgur. Mix with chopped herbs and veggies. Season with oil and lemon.</p>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tabouli with Extra Parsley', '" + tabouliEn + "');");

        String generalLunchEn = "<h1>Recipe</h1><p>Preparation details coming soon. Enjoy your healthy meal!</p>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Hummus and Veggie Wrap', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Sweet Potato and Black Bean Chili', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Zucchini Noodles with Pesto', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Quinoa and Black Bean Bowl', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Farro Salad with Dried Cranberries', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Red Lentil Pasta with Marinara', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Lentil Shepherd''s Pie (Vegan)', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Buddha Bowl with Chickpeas', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Vegetable Barley Soup', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Falafel Wrap with Hummus', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Split Pea Soup', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Minestrone Soup', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Black-Eyed Pea Salad', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Vegetable Paella', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Cabbage Soup with Potatoes', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Mediterranean Chickpea Salad', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Vegetable Korma', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('White Bean and Kale Soup', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Green Lentil and Rice (Mujadara)', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Roasted Chickpea Salad', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Three Bean Chili', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Brown Rice and Veggie Sushi', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tomato and Lentil Stew', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Quinoa with Pomegranate', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Barley and Mushroom Soup', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tofu and Broccoli with Peanut Sauce', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Mexican Quinoa Bowl', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Steamed Broccoli and Baked Tofu', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Lentil Soup with Kale', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Mixed Green Salad with Seeds', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Vegetable Stir-fry with Tempeh', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Roasted Cauliflower with Tahini', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Baked Sweet Potato with Greens', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Roasted Roots with Garlic Dip', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Kale and Quinoa Salad', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Baked Asparagus with Almonds', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Stuffed Peppers with Wild Rice', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Mushroom Risotto (Brown Rice)', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Eggplant Lasagna (No-Cheese)', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Sweet and Sour Tofu', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Brussels Sprouts with Balsamic', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Stir-fried Bok Choy and Tempeh', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Baked Squash with Quinoa', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Grilled Portobello Steaks', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Spiced Cauliflower Steaks', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Broccoli and Cashew Stir-fry', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Balsamic Glazed Beets', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Cabbage and Carrot Slaw', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Steamed Artichokes', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Baked Sweet Potato Wedges', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Garlic Sauteed Green Beans', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Miso Soup with Tofu', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Stir-fried Snap Peas', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Zucchini and Corn Sauté', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Steamed Mixed Vegetables', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Baked Acorn Squash', '" + generalLunchEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Sautéed Swiss Chard with Garlic', '" + generalLunchEn + "');");

        // PT Recipes - Breakfasts
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Aveia com Mirtilos e Nozes', '<h1>Aveia com Mirtilos e Nozes</h1><h3>Ingredientes</h3><ul><li>1/2 xícara de aveia</li><li>1 xícara de leite vegetal</li><li>1/2 xícara de mirtilos</li><li>1/4 xícara de nozes</li></ul><h3>Instruções</h3><p>Cozinhe a aveia por 5-7 min. Cubra com mirtilos e nozes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Panquecas Integrais com Frutas Frescas', '<h1>Panquecas Integrais com Frutas Frescas</h1><h3>Ingredientes</h3><ul><li>1 xícara farinha integral</li><li>1 c. sopa fermento</li><li>Leite vegetal e fruta</li></ul><h3>Instruções</h3><p>Misture e cozinhe. Sirva com fruta fresca.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Smoothie de Frutas com Sementes de Linhaça', '<h1>Smoothie de Frutas com Sementes de Linhaça</h1><h3>Ingredientes</h3><ul><li>1 banana</li><li>1 xícara frutos vermelhos</li><li>1 c. sopa linhaça</li></ul><h3>Instruções</h3><p>Bata tudo no liquidificador até ficar cremoso.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Papa de Trigo Sarraceno com Amêndoas', '<h1>Papa de Trigo Sarraceno com Amêndoas</h1><h3>Ingredientes</h3><ul><li>1/2 xícara trigo sarraceno</li><li>1.5 xícaras leite vegetal</li><li>Amêndoas</li></ul><h3>Instruções</h3><p>Cozinhe o trigo no leite por 15-20 min. Sirva com amêndoas.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Pudim de Chia com Manga', '<h1>Pudim de Chia com Manga</h1><h3>Ingredientes</h3><ul><li>3 c. sopa chia</li><li>1 xícara leite vegetal</li><li>Manga</li></ul><h3>Instruções</h3><p>Misture chia e leite. Leve ao frio por 4h. Junte a manga.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Torrada Integral com Abacate', '<h1>Torrada Integral com Abacate</h1><h3>Ingredientes</h3><ul><li>2 fatias pão integral</li><li>1 abacate</li></ul><h3>Instruções</h3><p>Torre o pão e barre com abacate esmagado.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Torrada Integral com Húmus', '<h1>Torrada Integral com Húmus</h1><h3>Ingredientes</h3><ul><li>2 fatias de pão integral</li><li>1/2 xícara de húmus</li><li>Fatias de pepino</li></ul><h3>Instruções</h3><ol><li>Torre o pão até ficar dourado.</li><li>Espalhe o húmus generosamente em cada fatia.</li><li>Cubra com pepino e sirva.</li></ol>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Painço com Tâmaras e Caju', '<h1>Painço com Tâmaras e Caju</h1><h3>Ingredientes</h3><ul><li>1/2 xícara painço</li><li>1.5 xícaras leite vegetal</li><li>Tâmaras e caju</li></ul><h3>Instruções</h3><p>Cozinhe o painço no leite por 20 min. Junte as tâmaras e caju.</p>');");
        
        String tofuScramblePt = "<h1>Mexido de Tofu com Vegetais</h1><h3>Ingredientes (para 2 pessoas):</h3><ul><li>1 bloco de tofu firme (aprox. 250g)</li><li>1 colher de sopa de azeite</li><li>Vegetais variados a gosto (ex: 1/2 cebola picada, 1 dente de alho, algumas folhas de espinafres, cogumelos laminados ou 1/2 curgete ralada)</li><li>1/2 colher de chá de açafrão-das-índias (curcuma) em pó</li><li>1 pitada de sal (pode usar sal negro/kala namak para simular o sabor a ovo)</li><li>Pimenta preta moída q.b.</li><li>Opcional: Levedura nutricional ou ervas frescas</li></ul><h3>Preparação:</h3><ol><li><b>Prepare o tofu:</b> Retire o excesso de água do tofu pressionando-o levemente com um guardanapo ou pano limpo. De seguida, esfarele-o com as mãos ou com a ajuda de um garfo.</li><li><b>Salteie os vegetais:</b> Numa frigideira, aqueça o azeite e salteie a cebola e o alho até dourarem. Adicione os restantes vegetais (como cogumelos e curgete) e deixe cozinhar até ficarem macios.</li><li><b>Junte o tofu:</b> Adicione o tofu esfarelado à frigideira e envolva tudo.</li><li><b>Tempere:</b> Polvilhe com o açafrão-das-índias, o sal e a pimenta preta. Misture bem e deixe cozinhar por cerca de 3 a 5 minutos, mexendo sempre.</li><li><b>Finalize:</b> Se preferir mais cremoso, pode juntar umas gotas de bebida vegetal ou água. Adicione levedura nutricional ou ervas frescas, se desejar.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Mexido de Tofu com Espinafres', '" + tofuScramblePt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Mexido de Tofu e Vegetais', '" + tofuScramblePt + "');");

        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Taça de Açaí com Granola Caseira', '<h1>Taça de Açaí</h1><p>Bata o açaí com banana. Cubra com granola e fruta.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Quinoa de Pequeno-Almoço com Bagas', '<h1>Quinoa com Bagas</h1><p>Cozinhe quinoa em leite vegetal e junte bagas frescas.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Torrada de Manteiga de Amendoim e Banana', '<h1>Torrada Amendoim</h1><p>Barre manteiga de amendoim no pão e junte banana.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Smoothie Bowl com Frutos Secos', '<h1>Smoothie Bowl</h1><p>Smoothie espesso decorado com frutos secos e sementes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Papas de Milho', '<h1>Papas de Milho</h1><p>Cozinhe fubá em leite vegetal até engrossar. Use canela.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Aveia com Maçã e Canela', '<h1>Aveia com Maçã</h1><p>Cozinhe aveia com pedaços de maçã e canela.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Burrito de Pequeno-Almoço (Feijão/Veg)', '<h1>Burrito Veg</h1><p>Tortilha com feijão preto e legumes salteados.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Abacate Esmagado em Pão de Centeio', '<h1>Abacate no Pão</h1><p>Esmague abacate sobre pão de centeio torrado.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Quinoa com Pêssegos', '<h1>Quinoa com Pêssego</h1><p>Misture quinoa cozida com fatias de pêssego fresco.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Panquecas de Banana (Farinha de Aveia)', '<h1>Panquecas de Banana</h1><p>Panquecas feitas com banana e farinha de aveia.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Aveia Adormecida com Abóbora', '<h1>Overnight Oats</h1><p>Aveia com puré de abóbora hidratada durante a noite.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Iogurte de Soja com Frutas Vermelhas', '<h1>Iogurte com Fruta</h1><p>Iogurte de soja com mix de frutos vermelhos.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Pão de Espelta com Manteiga de Frutos Secos', '<h1>Pão de Espelta</h1><p>Pão de espelta torrado com manteiga de amêndoa.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Papas de Pera e Noz', '<h1>Papas de Pera</h1><p>Aveia cremosa com pera fatiada e nozes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Smoothie de Espinafres e Fruta', '<h1>Smoothie Verde</h1><p>Bata espinafres com manga e água.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Trigo Sarraceno com Ervas Aromáticas', '<h1>Trigo Sarraceno</h1><p>Trigo sarraceno cozido com ervas frescas e sal.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salada de Fruta com Sementes de Cânhamo', '<h1>Salada de Fruta</h1><p>Fruta variada com sementes de cânhamo.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Parfait de Frutas Vermelhas (Vegan)', '<h1>Parfait Vegan</h1><p>Camadas de iogurte, fruta e granola.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Aveia em Grão com Figos', '<h1>Aveia com Figos</h1><p>Aveia integral com figos frescos.</p>');");

        // --- PT - ALMOÇOS E JANTARES COMPLETOS ---
        String quinoaSaladPt = "<h1>Salada de Quinoa com Legumes Assados</h1><h3>Ingredientes:</h3><ul><li>1 chávena de quinoa cozida</li><li>1 beringela pequena, 1 curgete e 1 pimento vermelho</li><li>Azeite, sal, pimenta e ervas da provence</li><li>Sumo de meio limão</li></ul><h3>Preparação:</h3><ol><li><b>Asse os legumes:</b> Corte os legumes em cubos, regue com azeite e ervas, e asse a 200°C por 20-25 min.</li><li><b>Misture:</b> Envolva os legumes assados com a quinoa cozida.</li><li><b>Finalize:</b> Tempere com limão e retifique o sal. Sirva morna ou fria.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salada de Quinoa com Legumes Assados', '" + quinoaSaladPt + "');");

        String tacosPt = "<h1>Tacos de Feijão Preto e Milho</h1><h3>Ingredientes:</h3><ul><li>4 tortilhas de milho</li><li>1 lata de feijão preto cozido</li><li>1 chávena de milho doce</li><li>Abacate, coentros e especiarias (cominhos e páprica)</li></ul><h3>Preparação:</h3><ol><li><b>Aqueça:</b> Aqueça o feijão e o milho com os temperos num tacho.</li><li><b>Monte:</b> Recheie as tortilhas com a mistura de feijão.</li><li><b>Finalize:</b> Adicione fatias de abacate e coentros picados.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tacos de Feijão Preto e Milho', '" + tacosPt + "');");

        String curryPt = "<h1>Caril de Grão-de-Bico com Arroz Integral</h1><h3>Ingredientes:</h3><ul><li>2 chávenas de grão-de-bico cozido</li><li>1 lata de leite de coco</li><li>1 colher de sopa de caril em pó</li><li>Arroz integral cozido</li><li>Cebola e alho picados</li></ul><h3>Preparação:</h3><ol><li><b>Refogado:</b> Refogue a cebola e o alho, junte o caril.</li><li><b>Cozinhe:</b> Adicione o grão e o leite de coco. Deixe apurar em lume brando por 10-15 min.</li><li><b>Sirva:</b> Coloque sobre uma base de arroz integral quente.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Caril de Grão-de-Bico com Arroz Integral', '" + curryPt + "');");

        String wrapPt = "<h1>Wrap de Húmus e Vegetais</h1><h3>Ingredientes:</h3><ul><li>1 tortilha grande integral</li><li>3 colheres de sopa de húmus</li><li>Cenoura ralada, alface e pepino em palitos</li></ul><h3>Preparação:</h3><ol><li><b>Barre:</b> Espalhe o húmus no centro da tortilha.</li><li><b>Recheie:</b> Disponha os vegetais frescos por cima.</li><li><b>Enrole:</b> Dobre as pontas e enrole firmemente.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Wrap de Húmus e Vegetais', '" + wrapPt + "');");

        String chiliPt = "<h1>Chili de Batata-Doce e Feijão Preto</h1><h3>Ingredientes:</h3><ul><li>2 batatas-doces médias (em cubos)</li><li>1 lata de feijão preto</li><li>1 chávena de polpa de tomate</li><li>Cebola, alho e especiarias (páprica e cominhos)</li></ul><h3>Preparação:</h3><ol><li><b>Cozinhe:</b> Leve a batata-doce ao lume com um pouco de água até amolecer.</li><li><b>Apure:</b> Junte o feijão, o tomate e os temperos.</li><li><b>Tempo:</b> Deixe cozinhar em lume brando por 15 min.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Chili de Batata-Doce e Feijão Preto', '" + chiliPt + "');");

        String stewPt = "<h1>Gisado de Lentilhas e Vegetais</h1><h3>Ingredientes:</h3><ul><li>1 chávena de lentilhas secas</li><li>Cenoura, batata e cebola picada</li><li>Caldo de legumes (aprox. 800ml)</li><li>Louro e azeite</li></ul><h3>Preparação:</h3><ol><li><b>Base:</b> Refogue a cebola com o azeite e o louro.</li><li><b>Cozzedura:</b> Adicione os vegetais, as lentilhas e o caldo.</li><li><b>Tempo:</b> Cozinhe tapado por 25-30 min até as lentilhas estarem macias.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Gisado de Lentilhas e Vegetais', '" + stewPt + "');");

        String zoodlesPt = "<h1>Zoodles (Abobrinha) com Pesto</h1><h3>Ingredientes:</h3><ul><li>2 curgetes grandes (em espirais)</li><li>1/2 chávena de molho pesto vegan</li><li>Tomates cherry para decorar</li></ul><h3>Preparação:</h3><ol><li><b>Salteie:</b> Leve os zoodles a uma frigideira com um fio de azeite por apenas 2 min.</li><li><b>Misture:</b> Retire do lume e envolva bem no molho pesto.</li><li><b>Finalize:</b> Sirva com os tomates cherry cortados ao meio.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Zoodles (Abobrinha) com Pesto', '" + zoodlesPt + "');");

        String tofuBroccoliPt = "<h1>Tofu e Brócolis com Molho de Amendoim</h1><h3>Ingredientes:</h3><ul><li>1 bloco de tofu firme em cubos</li><li>2 chávenas de flores de brócolos</li><li>2 colheres de sopa de manteiga de amendoim</li><li>1 colher de sopa de molho de soja e gengibre</li></ul><h3>Preparação:</h3><ol><li><b>Tofu:</b> Grelhe o tofu numa frigideira até dourar.</li><li><b>Brócolos:</b> Coza os brócolos ao vapor para que fiquem crocantes.</li><li><b>Molho:</b> Misture a manteiga de amendoim, soja, gengibre e um pouco de água.</li><li><b>Envolva:</b> Junte tudo e sirva de imediato.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tofu e Brócolis com Molho de Amendoim', '" + tofuBroccoliPt + "');");

        String soupPt = "<h1>Sopa de Lentilha com Couve</h1><h3>Ingredientes:</h3><ul><li>Lentilhas castanhas</li><li>Couve galega picada</li><li>Cebola e alho</li></ul><h3>Preparação:</h3><ol><li><b>Base:</b> Coza as lentilhas com um refogado de cebola e alho.</li><li><b>Finalize:</b> Quando as lentilhas estiverem quase prontas, junte a couve e cozinhe mais 5 min.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Sopa de Lentilha com Couve', '" + soupPt + "');");

        String stirFryPt = "<h1>Salteado de Vegetais com Tempeh</h1><h3>Ingredientes:</h3><ul><li>200g de tempeh em tiras</li><li>Mix de vegetais (pimento, cenoura, brócolos)</li><li>Molho de soja</li></ul><h3>Preparação:</h3><ol><li><b>Tempeh:</b> Doure o tempeh numa frigideira com um pouco de óleo.</li><li><b>Salteie:</b> Adicione os vegetais e cozinhe em lume forte, mexendo sempre.</li><li><b>Tempere:</b> Finalize com molho de soja e sirva.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salteado de Vegetais com Tempeh', '" + stirFryPt + "');");

        String tabulePt = "<h1>Tabule com Salsa Extra</h1><h3>Ingredientes:</h3><ul><li>Trigo bulgur, muita salsa, hortelã, tomate e pepino</li></ul><h3>Preparação:</h3><p>Hidrate o bulgur e misture com as ervas picadas e temperos.</p>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tabule com Salsa Extra', '" + tabulePt + "');");

        String generalLunchPt = "<h1>Receita</h1><p>Modo de preparação detalhado em breve. Desfrute da sua refeição saudável!</p>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Bowl de Quinoa e Feijão Preto', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salada de Farro com Arandos Secos', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Massa de Lentilha Vermelha com Marinara', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Empadão de Lentilha (Vegan)', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Bowl Buddha com Grão-de-Bico', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Sopa de Cevada e Vegetais', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Wrap de Falafel com Húmus', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Sopa de Ervilha', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Sopa Minestrone', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salada de Feijão Frade', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Paella de Vegetais', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Sopa de Repolho com Batatas', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salada de Grão Mediterrânea', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Korma de Vegetais', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Sopa de Feijão Branco e Couve', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Lentilha Verde e Arroz (Mujadara)', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salada de Grão-de-Bico Assado', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Three Bean Chili', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Brown Rice and Veggie Sushi', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tomato and Lentil Stew', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Quinoa with Pomegranate', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Barley and Mushroom Soup', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Mexican Quinoa Bowl', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Brócolis a Vapor e Tofu Grelhado', '<h1>Tofu com Brócolos</h1><p>Brócolos ao vapor com cubos de tofu grelhados.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salada Verde Mista com Sementes', '<h1>Salada Mista</h1><p>Mix de folhas verdes com sementes tostadas.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Couve-Flor Assada com Tahini', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Batata-Doce Assada com Folhas Verdes', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Raízes Assadas com Molho de Alho', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salada de Couve e Quinoa', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Espargos Assados com Amêndoas', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Pimentos Recheados com Arroz Selvagem', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Risoto de Cogumelos (Arroz Integral)', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Lasanha de Beringela (Sem Queijo)', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tofu Agridoce', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Couve de Bruxelas com Balsâmico', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Bok Choy Salteado com Tempeh', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Abóbora Assada com Quinoa', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Bifes de Cogumelo Portobello', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Bifes de Couve-Flor Especiados', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salteado de Brócolis e Caju', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Beterraba Glaciada com Balsâmico', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salada de Repolho e Cenoura', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Alcachofras ao Vapor', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Batata-Doce em Palitos Assada', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Vagem Salteada com Alho', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Sopa Miso com Tofu', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Ervilhas de Quebrar Salteadas', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salteado de Abobrinha e Milho', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Mistura de Vegetais ao Vapor', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Abóbora Menina Assada', '" + generalLunchPt + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Acelga Salteada com Alho', '" + generalLunchPt + "');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 76) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_RECIPES + " (" + COL_RECIPE_TITLE + " TEXT PRIMARY KEY, " + COL_RECIPE_CONTENT + " TEXT);");
            insertInitialRecipes(db);
        }
    }

    public String getRecipeContent(String title) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT " + COL_RECIPE_CONTENT + " FROM " + TABLE_RECIPES + " WHERE " + COL_RECIPE_TITLE + " = ?", new String[]{title});
            String content = null;
            if (cursor != null) {
                if (cursor.moveToFirst()) content = cursor.getString(0);
                cursor.close();
            }
            return content;
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getSettingsLanguage() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_S_LANG + " WHERE " + COL_S_INDEX_L + " = 1";
        return db.rawQuery(query, null);
    }

    public void setSettingsLanguage(String language) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("language", language);
        db.update(TABLE_S_LANG, cv, COL_S_INDEX_L + " = 1", null);
    }
}
