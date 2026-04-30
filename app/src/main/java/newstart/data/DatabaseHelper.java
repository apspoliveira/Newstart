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
    private static final int DATABASE_VERSION = 111;

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
        // --- PORTUGUESE RECIPES ---

        // Breakfasts
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Aveia com Mirtilos e Nozes', '<h1>Aveia com Mirtilos e Nozes</h1><h3>Ingredientes:</h3><ul><li>1/2 xícara de aveia</li><li>1 xícara de leite vegetal</li><li>Mirtilos frescos e nozes picadas</li></ul><h3>Instruções:</h3><p>Cozinhe a aveia no leite por 5 min. Cubra com os frutos.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Panquecas Integrais com Frutas Frescas', '<h1>Panquecas Integrais</h1><h3>Ingredientes:</h3><ul><li>1 xícara farinha integral</li><li>1 banana madura</li><li>Leite vegetal</li></ul><h3>Instruções:</h3><p>Misture tudo e cozinhe numa frigideira antiaderente.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Smoothie de Frutas com Sementes de Linhaça', '<h1>Smoothie de Frutas</h1><h3>Ingredientes:</h3><ul><li>1 banana</li><li>1 xícara de bagas mistas</li><li>1 c. sopa de linhaça moída</li><li>250ml de leite vegetal</li></ul><h3>Preparação:</h3><p>Bata todos os ingredientes no liquidificador até ficar cremoso.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Papa de Trigo Sarraceno com Amêndoas', '<h1>Papa de Trigo Sarraceno</h1><h3>Ingredientes:</h3><ul><li>1/2 xícara trigo sarraceno</li><li>1.5 xícaras de leite vegetal</li><li>Amêndoas torradas</li></ul><h3>Preparação:</h3><p>Cozinhe o trigo no leite por 20 min em lume brando. Sirva com as amêndoas por cima.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Pudim de Chia com Manga', '<h1>Pudim de Chia</h1><h3>Ingredientes:</h3><ul><li>3 c. sopa de sementes de chia</li><li>1 xícara de leite de coco</li><li>1/2 manga fresca picada</li></ul><h3>Preparação:</h3><p>Misture a chia com o leite. Deixe repousar no frigorífico por 4h. Sirva com a manga.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Torrada Integral com Abacate', '<h1>Torrada com Abacate</h1><h3>Ingredientes:</h3><ul><li>2 fatias de pão integral</li><li>1 abacate maduro</li><li>Sal, pimenta e limão</li></ul><h3>Preparação:</h3><p>Torre o pão. Esmague o abacate com um pouco de sumo de limão e espalhe sobre o pão.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Torrada Integral com Húmus', '<h1>Torrada com Húmus</h1><h3>Ingredientes:</h3><ul><li>2 fatias de pão integral</li><li>4 c. sopa de húmus</li><li>Rodelas de pepino</li></ul><h3>Preparação:</h3><p>Torre o pão, barre com o húmus e decore com o pepino fresco.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Painço com Tâmaras e Caju', '<h1>Painço com Tâmaras</h1><h3>Ingredientes:</h3><ul><li>1/2 xícara de painço</li><li>2 xícaras de leite vegetal</li><li>Tâmaras e cajus picados</li></ul><h3>Preparação:</h3><p>Cozinhe o painço no leite (20 min). Adicione as tâmaras e os cajus no final.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Taça de Açaí com Granola Caseira', '<h1>Taça de Açaí</h1><h3>Ingredientes:</h3><ul><li>200g polpa de açaí congelada</li><li>1 banana madura</li><li>1/2 chávena de granola</li><li>Frutas frescas</li></ul><h3>Preparação:</h3><p>Bata o açaí e a banana até ficar cremoso. Coloque numa taça e decore com granola e fruta.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Quinoa de Pequeno-Almoço com Bagas', '<h1>Quinoa com Bagas</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena de quinoa cozida</li><li>1/2 chávena de leite vegetal</li><li>Mix de bagas e nozes</li><li>1 colher de sopa de ácer</li></ul><h3>Preparação:</h3><p>Aqueça a quinoa no leite vegetal. Sirva com as bagas e nozes por cima.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Torrada de Manteiga de Amendoim e Banana', '<h1>Torrada com Banana</h1><h3>Ingredientes:</h3><ul><li>2 fatias de pão integral</li><li>2 colheres de manteiga de amendoim</li><li>1 banana fatiada</li><li>Sementes de chia</li></ul><h3>Preparação:</h3><p>Torre o pão, barre com the manteiga de amendoim e disponha a banana. Polvilhe com chia.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Smoothie Bowl com Frutos Secos', '<h1>Smoothie Bowl</h1><h3>Ingredientes:</h3><ul><li>Manga e banana congeladas</li><li>1/2 chávena de iogurte vegetal</li><li>Mix de nozes e sementes</li></ul><h3>Preparação:</h3><p>Bata a fruta com o iogurte até ficar espesso. Sirva numa taça com os frutos secos.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Papas de Milho', '<h1>Papas de Milho</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena de sêmola de milho</li><li>2 chávenas de leite vegetal</li><li>Pitada de sal e canela</li></ul><h3>Preparação:</h3><p>Coza o milho no leite mexendo sempre até engrossar. Polvilhe com canela no final.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Aveia com Maçã e Canela', '<h1>Aveia com Maçã</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena de aveia</li><li>1 maçã ralada</li><li>1 chávena de água ou leite</li><li>Canela em pó</li></ul><h3>Preparação:</h3><p>Coza a aveia com a maçã por 5 min. Junte a canela e envolva bem antes de servir.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Burrito de Pequeno-Almoço (Feijão/Veg)', '<h1>Burrito Veggie</h1><h3>Ingredientes:</h3><ul><li>1 tortilha integral</li><li>1/2 chávena de feijão preto</li><li>Pimento e cebola salteados</li><li>Abacate</li></ul><h3>Preparação:</h3><p>Aqueça a tortilha, recheie com os vegetais e o feijão. Enrole e sirva quente.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Abacate Esmagado em Pão de Centeio', '<h1>Abacate no Pão</h1><h3>Ingredientes:</h3><ul><li>2 fatias de pão de centeio</li><li>1 abacate maduro</li><li>Lima e sementes de sésamo</li></ul><h3>Preparação:</h3><p>Torre o pão. Esmague o abacate com lima e barre no pão. Finalize com sésamo.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Quinoa com Pêssegos', '<h1>Quinoa com Pêssego</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena de quinoa cozida</li><li>1 pêssego fatiado</li><li>Sementes de cânhamo</li></ul><h3>Preparação:</h3><p>Misture a quinoa cozida com o pêssego fresco e polvilhe com as sementes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Panquecas de Banana (Farinha de Aveia)', '<h1>Panquecas de Banana</h1><h3>Ingredientes:</h3><ul><li>1 banana</li><li>1/2 chávena de farinha de aveia</li><li>1/4 chávena de leite vegetal</li></ul><h3>Preparação:</h3><p>Bata tudo no liquidificador e cozinhe pequenas porções numa frigideira antiaderente.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Aveia Adormecida com Abóbora', '<h1>Aveia com Abóbora</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena aveia</li><li>1/4 chávena puré abóbora</li><li>1/2 chávena leite vegetal</li></ul><h3>Preparação:</h3><p>Misture tudo num frasco e deixe no frigorífico durante a noite. Coma fresco.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Iogurte de Soja com Frutas Vermelhas', '<h1>Iogurte com Fruta</h1><h3>Ingredientes:</h3><ul><li>1 chávena iogurte soja</li><li>Bagas frescas</li><li>Linhaça moída</li></ul><h3>Preparação:</h3><p>Coloque o iogurte numa taça e junte as bagas e a linhaça por cima.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Pão de Espelta com Manteiga de Frutos Secos', '<h1>Pão com Manteiga</h1><h3>Ingredientes:</h3><ul><li>2 fatias pão espelta</li><li>Manteiga de amêndoa</li><li>Mirtilos frescos</li></ul><h3>Preparação:</h3><p>Torre o pão e barre com a manteiga. Junte mirtilos para adoçar.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tofu and Veggie Hash', '<h1>Mexido de Tofu</h1><h3>Ingredientes:</h3><ul><li>1/2 bloco de tofu</li><li>Batata em cubos</li><li>Pimento e alho</li></ul><h3>Preparação:</h3><p>Salteie a batata e vegetais. Junte o tofu esfarelado e cozinhe 5 min.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Papas de Pera e Noz', '<h1>Papas de Pera</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena aveia</li><li>1 pera fatiada</li><li>Nozes picadas</li></ul><h3>Preparação:</h3><p>Coza a aveia no leite e sirva decorado com a pera e as nozes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Smoothie with Spinach/Fruit', '<h1>Smoothie Verde</h1><h3>Ingredientes:</h3><ul><li>Espinafres, manga e banana</li><li>1/2 chávena de água</li></ul><h3>Preparação:</h3><p>Bata todos os ingredientes até obter um batido cremoso e homogéneo.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Buckwheat with Savory Herbs', '<h1>Trigo com Ervas</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena trigo sarraceno</li><li>Salsa, coentros e tomate cherry</li></ul><h3>Preparação:</h3><p>Coza o trigo e envolva-o com as ervas aromáticas picadas e os tomates.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Fruit Salad with Hemp Seeds', '<h1>Salada com Sementes</h1><h3>Ingredientes:</h3><ul><li>Mix de frutas sazonais</li><li>Sementes de cânhamo</li></ul><h3>Preparação:</h3><p>Corte as frutas numa taça e polvilhe com as sementes de cânhamo nutritivas.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Mixed Berry Parfait (Vegan)', '<h1>Parfait Vegan</h1><h3>Ingredientes:</h3><ul><li>Iogurte de coco</li><li>Granola e bagas mistas</li></ul><h3>Preparação:</h3><p>Monte em camadas num copo: primeiro iogurte, depois fruta e por fim granola.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Steel Cut Oats with Figs', '<h1>Aveia com Figos</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena aveia em grão</li><li>Figos frescos e canela</li></ul><h3>Preparação:</h3><p>Coza a aveia lentamente (20 min). Sirva com figos fatiados e canela.</p>');");

        String tofuScramblePtFull = "<h1>Mexido de Tofu com Vegetais</h1><h3>Ingredientes:</h3><ul><li>1 bloco de tofu firme</li><li>1 colher de sopa de azeite</li><li>Vegetais: cebola, alho, espinafres, cogumelos</li><li>1/2 c. chá de açafrão, salt e pimenta</li></ul><h3>Preparação:</h3><ol><li>Esmague o tofu. Salteie os vegetais no azeite.</li><li>Junte o tofu e temperos. Cozinhe por 5 min mexendo sempre.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Mexido de Tofu com Espinafres', '" + tofuScramblePtFull + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Mexido de Tofu e Vegetais', '" + tofuScramblePtFull + "');");

        // Almoços e Jantares (PT)
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salada de Quinoa com Legumes Assados', '<h1>Salada de Quinoa</h1><h3>Ingredientes:</h3><ul><li>1 chávena de quinoa</li><li>Beringela, curgete e pimento em cubos</li><li>Azeite e limão</li></ul><h3>Preparação:</h3><p>Asse os legumes a 200°C. Misture com a quinoa cozida e tempere com limão.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tacos de Feijão Preto e Milho', '<h1>Tacos de Feijão</h1><h3>Ingredientes:</h3><ul><li>Tortilhas de milho</li><li>Feijão preto, milho, abacate</li></ul><h3>Preparação:</h3><p>Aqueça o feijão com cominhos. Recheie as tortilhas com os ingredientes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Caril de Grão-de-Bico com Arroz Integral', '<h1>Caril de Grão</h1><h3>Ingredientes:</h3><ul><li>Grão cozido, leite de coco, caril e arroz</li></ul><h3>Preparação:</h3><p>Refogue cebola e caril. Junte o grão e leite de coco. Cozinhe 10 min e sirva com arroz.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Wrap de Húmus e Vegetais', '<h1>Wrap de Húmus</h1><h3>Ingredientes:</h3><ul><li>Tortilha, húmus e vegetais frescos</li></ul><h3>Preparação:</h3><p>Barre o húmus na tortilha e recheie com vegetais crocantes. Enrole.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Chili de Batata-Doce e Feijão Preto', '<h1>Chili de Batata-Doce</h1><h3>Ingredientes:</h3><ul><li>Batata-doces, feijão preto e tomate</li></ul><h3>Preparação:</h3><p>Cozinhe tudo num tacho com especiarias até a batata estar macia.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Gisado de Lentilhas e Vegetais', '<h1>Gisado de Lentilhas</h1><h3>Ingredientes:</h3><ul><li>Lentilhas, cenoura e batata</li></ul><h3>Preparação:</h3><p>Cozinhe em caldo de legumes até as lentilhas estarem macias.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Zoodles (Abobrinha) com Pesto', '<h1>Zoodles com Pesto</h1><h3>Ingredientes:</h3><ul><li>Curgete em espirais e pesto vegan</li></ul><h3>Preparação:</h3><p>Salteie a curgete por 2 min e envolva no molho pesto.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Bowl de Quinoa e Feijão Preto', '<h1>Bowl de Quinoa</h1><h3>Ingredientes:</h3><ul><li>Quinoa, feijão, milho e abacate</li></ul><h3>Preparação:</h3><p>Misture os ingredientes numa taça e tempere com lima.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salada de Farro com Arandos Secos', '<h1>Salada de Farro</h1><h3>Ingredientes:</h3><ul><li>Farro cozido, arandos e nozes</li></ul><h3>Preparação:</h3><p>Misture os ingredientes e tempere com vinagrete.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Massa de Lentilha Vermelha com Marinara', '<h1>Massa de Lentilha</h1><h3>Ingredientes:</h3><ul><li>Massa de lentilha, molho tomate, manjericão</li></ul><h3>Preparação:</h3><p>Coza a massa e envolva no molho de tomate temperado.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Empadão de Lentilha (Vegan)', '<h1>Empadão de Lentilha</h1><h3>Ingredientes:</h3><ul><li>Lentilhas estufadas e puré de batata</li></ul><h3>Preparação:</h3><p>Cubra as lentilhas com puré e leve ao forno a dourar.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Bowl Buddha com Grão-de-Bico', '<h1>Bowl Buddha</h1><h3>Ingredientes:</h3><ul><li>Grão assado, quinoa e espinafres</li></ul><h3>Preparação:</h3><p>Disponha em sectores e regue com molho de tahine.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Sopa de Cevada e Vegetais', '<h1>Sopa de Cevada</h1><h3>Ingredientes:</h3><ul><li>Cevada perlada, cenoura e batata</li></ul><h3>Preparação:</h3><p>Coza a cevada com os vegetais picados num caldo.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Wrap de Falafel com Húmus', '<h1>Wrap de Falafel</h1><h3>Ingredientes:</h3><ul><li>Falafel, tortilha, húmus e salada</li></ul><h3>Preparação:</h3><p>Barre a tortilha com húmus, coloque falafel e salada. Enrole.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Sopa de Ervilha', '<h1>Sopa de Ervilha</h1><h3>Ingredientes:</h3><ul><li>Ervilhas secas e cebola</li></ul><h3>Preparação:</h3><p>Coza até as ervilhas desfazerem e triture com hortelã.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tabule com Salsa Extra', '<h1>Tabule</h1><h3>Ingredientes:</h3><ul><li>Bulgur, muita salsa picada, hortelã e tomate</li></ul><h3>Preparação:</h3><p>Hidrate o bulgur e envolva com os vegetais picados.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Sopa Minestrone', '<h1>Sopa Minestrone</h1><h3>Ingredientes:</h3><ul><li>Feijão branco, massa e legumes</li></ul><h3>Preparação:</h3><p>Coza os legumes com tomate, junte feijão e massa.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salada de Feijão Frade', '<h1>Salada de Feijão Frade</h1><h3>Ingredientes:</h3><ul><li>Feijão frade, cebola e salsa</li></ul><h3>Preparação:</h3><p>Misture o feijão com cebola picada, salsa e azeite.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Paella de Vegetais', '<h1>Paella de Vegetais</h1><h3>Ingredientes:</h3><ul><li>Arroz, açafrão, ervilhas e pimentos</li></ul><h3>Preparação:</h3><p>Cozinhe o arroz no caldo com legumes e açafrão.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Sopa de Repolho com Batatas', '<h1>Sopa de Repolho</h1><h3>Ingredientes:</h3><ul><li>Repolho, batata e alho</li></ul><h3>Preparação:</h3><p>Coza tudo e finalize com azeite.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salada de Grão Mediterrânea', '<h1>Salada de Grão</h1><h3>Ingredientes:</h3><ul><li>Grão cozido, tomate, pepino e azeitonas</li></ul><h3>Preparação:</h3><p>Misture tudo e tempere com azeite e limão.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Korma de Vegetais', '<h1>Korma de Vegetais</h1><h3>Ingredientes:</h3><ul><li>Mix de vegetais e leite de coco</li></ul><h3>Preparação:</h3><p>Estufe os vegetais no leite de coco com caril.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Sopa de Feijão Branco e Couve', '<h1>Sopa de Feijão e Couve</h1><h3>Ingredientes:</h3><ul><li>Feijão branco e couve</li></ul><h3>Preparação:</h3><p>Coza o feijão e junte a couve no final.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Lentilha Verde e Arroz (Mujadara)', '<h1>Mujadara</h1><h3>Ingredientes:</h3><ul><li>Lentilhas, arroz e cebola</li></ul><h3>Preparação:</h3><p>Coza juntos e cubra com cebola frita.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salada de Grão-de-Bico Assado', '<h1>Salada de Grão Assado</h1><h3>Ingredientes:</h3><ul><li>Grão assado crocante e folhas verdes</li></ul><h3>Preparação:</h3><p>Asse o grão e misture com a salada fresca.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Three Bean Chili', '<h1>Three Bean Chili</h1><h3>Ingredientes:</h3><ul><li>3 tipos de feijão e tomate</li></ul><h3>Preparação:</h3><p>Cozinhe os feijões num molho de tomate temperado.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Brown Rice and Veggie Sushi', '<h1>Sushi Veggie</h1><h3>Ingredientes:</h3><ul><li>Arroz integral e vegetais</li></ul><h3>Preparação:</h3><p>Enrole o arroz e vegetais em folhas de nori.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Gisado de Tomate e Lentilhas', '<h1>Gisado de Tomate</h1><h3>Ingredientes:</h3><ul><li>Lentilhas e tomate</li></ul><h3>Preparação:</h3><p>Cozinhe as lentilhas no molho de tomate.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Quinoa com Romã', '<h1>Quinoa com Romã</h1><h3>Ingredientes:</h3><ul><li>Quinoa e romã</li></ul><h3>Preparação:</h3><p>Misture a quinoa cozida com bagos de romã.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Barley and Mushroom Soup', '<h1>Sopa de Cevada e Cogumelos</h1><h3>Ingredientes:</h3><ul><li>Cevada e cogumelos</li></ul><h3>Preparação:</h3><p>Coza a cevada com o mix de cogumelos.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Sopa de Cevada e Cogumelos', '<h1>Sopa de Cevada e Cogumelos</h1><h3>Ingredientes:</h3><ul><li>Cevada e cogumelos</li></ul><h3>Preparação:</h3><p>Coza a cevada com o mix de cogumelos.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tofu and Broccoli with Peanut Sauce', '<h1>Tofu com Amendoim</h1><h3>Ingredientes:</h3><ul><li>Tofu, brócolos e amendoim</li></ul><h3>Preparação:</h3><p>Misture o tofu e brócolos com o molho de amendoim.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Mexican Quinoa Bowl', '<h1>Mexican Bowl</h1><h3>Ingredientes:</h3><ul><li>Quinoa, feijão e milho</li></ul><h3>Preparação:</h3><p>Misture tudo com molho de tomate e salsa.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Brócolis a Vapor e Tofu Grelhado', '<h1>Brócolis e Tofu</h1><h3>Ingredientes:</h3><ul><li>Brócolis, tofu firme, alho</li></ul><h3>Preparação:</h3><p>Coza os brócolis ao vapor e sirva com tofu grelhado.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salada Verde Mista com Sementes', '<h1>Salada com Sementes</h1><h3>Ingredientes:</h3><ul><li>Mix de folhas e sementes</li></ul><h3>Preparação:</h3><p>Misture as folhas e junte sementes tostadas.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Vegetable Stir-fry with Tempeh', '<h1>Salteado de Tempeh</h1><h3>Ingredientes:</h3><ul><li>Tempeh e vegetais</li></ul><h3>Preparação:</h3><p>Salteie tudo com soja.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Roasted Cauliflower with Tahini', '<h1>Couve-Flor com Tahini</h1><h3>Ingredientes:</h3><ul><li>Couve-flor e sésamo</li></ul><h3>Preparação:</h3><p>Asse e cubra com molho.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Baked Sweet Potato with Greens', '<h1>Batata-Doce com Verdes</h1><h3>Ingredientes:</h3><ul><li>Batata-doce e couve</li></ul><h3>Preparação:</h3><p>Asse e recheie com verdes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Roasted Roots with Garlic Dip', '<h1>Raízes com Alho</h1><h3>Ingredientes:</h3><ul><li>Cenoura e alho</li></ul><h3>Preparação:</h3><p>Asse e sirva com dip.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salada de Couve e Quinoa', '<h1>Salada de Couve</h1><h3>Ingredientes:</h3><ul><li>Couve e quinoa</li></ul><h3>Preparação:</h3><p>Misture a couve massajada com quinoa.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Espargos Assados com Amêndoas', '<h1>Espargos com Amêndoas</h1><h3>Ingredientes:</h3><ul><li>Espargos e amêndoas</li></ul><h3>Preparação:</h3><p>Asse os espargos e junte amêndoas.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Pimentos Recheados with Wild Rice', '<h1>Pimentos Recheados</h1><h3>Ingredientes:</h3><ul><li>Pimentos e arroz</li></ul><h3>Preparação:</h3><p>Recheie os pimentos e leve ao forno.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Risoto de Cogumelos (Arroz Integral)', '<h1>Risoto de Cogumelos</h1><h3>Ingredientes:</h3><ul><li>Arroz e cogumelos</li></ul><h3>Preparação:</h3><p>Cozinhe o arroz com caldo e cogumelos.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Lasanha de Beringela (Sem Queijo)', '<h1>Lasanha de Beringela</h1><h3>Ingredientes:</h3><ul><li>Beringela e tomate</li></ul><h3>Preparação:</h3><p>Monte camadas e asse no forno.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tofu Agridoce', '<h1>Tofu Agridoce</h1><h3>Ingredientes:</h3><ul><li>Tofu e molho agridoce</li></ul><h3>Preparação:</h3><p>Frite o tofu e envolva no molho.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Couve de Bruxelas com Balsâmico', '<h1>Bruxelas Balsâmico</h1><h3>Ingredientes:</h3><ul><li>Couves e balsâmico</li></ul><h3>Preparação:</h3><p>Asse as couves e regue com balsâmico.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Bok Choy Salteado with Tempeh', '<h1>Bok Choy Tempeh</h1><h3>Ingredientes:</h3><ul><li>Bok choy e tempeh</li></ul><h3>Preparação:</h3><p>Salteie com gengibre e sementes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Abóbora Assada with Quinoa', '<h1>Abóbora com Quinoa</h1><h3>Ingredientes:</h3><ul><li>Abóbora e quinoa</li></ul><h3>Preparação:</h3><p>Recheie a abóbora assada.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Bifes de Cogumelo Portobello', '<h1>Bifes Portobello</h1><h3>Ingredientes:</h3><ul><li>Portobello e alho</li></ul><h3>Preparação:</h3><p>Grelhe os cogumelos inteiros.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Bifes de Couve-Flor Especiados', '<h1>Bifes de Couve-Flor</h1><h3>Ingredientes:</h3><ul><li>Couve-flor e especiarias</li></ul><h3>Preparação:</h3><p>Asse fatias de couve-flor.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salteado de Brócolis e Caju', '<h1>Salteado com Caju</h1><h3>Ingredientes:</h3><ul><li>Brócolis e cajus</li></ul><h3>Preparação:</h3><p>Salteie tudo com soja.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Beterraba Glaciada com Balsâmico', '<h1>Beterraba Glaciada</h1><h3>Ingredientes:</h3><ul><li>Beterraba</li></ul><h3>Preparação:</h3><p>Glaciar no forno com balsâmico.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salada de Repolho e Cenoura', '<h1>Salada de Repolho</h1><h3>Ingredientes:</h3><ul><li>Repolho e cenoura</li></ul><h3>Preparação:</h3><p>Misture com molho leve.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Alcachofras ao Vapor', '<h1>Alcachofras</h1><h3>Ingredientes:</h3><ul><li>Alcachofras</li></ul><h3>Preparação:</h3><p>Coza ao vapor com limão.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Batata-Doce em Palitos Assada', '<h1>Palitos de Batata</h1><h3>Ingredientes:</h3><ul><li>Batata-doce e páprica</li></ul><h3>Preparação:</h3><p>Asse até dourar.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Vagem Salteada com Alho', '<h1>Vagem com Alho</h1><h3>Ingredientes:</h3><ul><li>Vagem e alho</li></ul><h3>Preparação:</h3><p>Salteie rapidamente.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Sopa Miso com Tofu', '<h1>Sopa Miso</h1><h3>Ingredientes:</h3><ul><li>Miso e tofu</li></ul><h3>Preparação:</h3><p>Prepare o caldo e sirva.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Ervilhas de Quebrar Salteadas', '<h1>Ervilhas Salteadas</h1><h3>Ingredientes:</h3><ul><li>Ervilhas</li></ul><h3>Preparação:</h3><p>Salteie em lume forte.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salteado de Abobrinha e Milho', '<h1>Curgete e Milho</h1><h3>Ingredientes:</h3><ul><li>Curgete e milho</li></ul><h3>Preparação:</h3><p>Salteie com ervas.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Mistura de Vegetais ao Vapor', '<h1>Mix de Vegetais</h1><h3>Ingredientes:</h3><ul><li>Mix de vegetais</li></ul><h3>Preparação:</h3><p>Coza ao vapor.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Abóbora Menina Assada', '<h1>Abóbora Assada</h1><h3>Ingredientes:</h3><ul><li>Abóbora</li></ul><h3>Preparação:</h3><p>Asse no forno.</p>');");
        
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Acelga Salteada com Alho', '<h1>Acelga Salteada com Alho</h1>" +
                "<h3>Ingredientes:</h3><ul>" +
                "<li>1 maço de acelga (lavada e cortada em tiras largas)</li>" +
                "<li>3 a 4 dentes de alho fatiados ou picados</li>" +
                "<li>2 colheres de sopa de azeite de oliva</li>" +
                "<li>Sal e pimenta-do-reino a gosto</li>" +
                "<li>Opcional: Um toque de pimenta calabresa ou gotas de limão ao finalizar.</li></ul>" +
                "<h3>Passo a Passo:</h3><ol>" +
                "<li><b>Prepare a Acelga:</b> Separe os talos brancos das folhas verdes. Corte os talos em fatias finas e as folhas em pedaços maiores.</li>" +
                "<li><b>Aromatize o Azeite:</b> Em uma frigideira grande ou wok, aqueça o azeite em fogo médio. Adicione o alho e deixe dourar levemente.</li>" +
                "<li><b>Refogue os Talos:</b> Adicione primeiro apenas os talos da acelga. Refogue por cerca de 2 a 3 minutos até que comecem a amaciar.</li>" +
                "<li><b>Adicione as Folhas:</b> Junte as folhas verdes. Elas vão ocupar muito espaço no início, mas murcham rápido. Tempere com sal e pimenta.</li>" +
                "<li><b>Finalize:</b> Refogue por mais 1 ou 2 minutos, mexendo sempre, até que as folhas estejam murchas mas ainda com um verde vibrante.</li></ol>');");

        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Taça de Açaí com Granola Caseira', '<h1>Taça de Açaí</h1><h3>Ingredientes:</h3><ul><li>200g polpa de açaí congelada</li><li>1 banana madura</li><li>1/2 chávena de granola</li><li>Frutas frescas</li></ul><h3>Preparação:</h3><p>Bata o açaí e a banana até ficar cremoso. Coloque numa taça e decore com granola e fruta.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Quinoa de Pequeno-Almoço com Bagas', '<h1>Quinoa com Bagas</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena de quinoa cozida</li><li>1/2 chávena de leite vegetal</li><li>Mix de bagas e nozes</li><li>1 colher de sopa de ácer</li></ul><h3>Preparação:</h3><p>Aqueça a quinoa no leite vegetal. Sirva com as bagas e nozes por cima.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Torrada de Manteiga de Amendoim e Banana', '<h1>Torrada com Banana</h1><h3>Ingredientes:</h3><ul><li>2 fatias de pão integral</li><li>2 colheres de manteiga de amendoim</li><li>1 banana fatiada</li><li>Sementes de chia</li></ul><h3>Preparação:</h3><p>Torre o pão, barre com the manteiga de amendoim e disponha a banana. Polvilhe com chia.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Smoothie Bowl com Frutos Secos', '<h1>Smoothie Bowl</h1><h3>Ingredientes:</h3><ul><li>Manga e banana congeladas</li><li>1/2 chávena de iogurte vegetal</li><li>Mix de nozes e sementes</li></ul><h3>Preparação:</h3><p>Bata a fruta com o iogurte até ficar espesso. Sirva numa taça com os frutos secos.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Papas de Milho', '<h1>Papas de Milho</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena de sêmola de milho</li><li>2 chávenas de leite vegetal</li><li>Pitada de sal e canela</li></ul><h3>Preparação:</h3><p>Coza o milho no leite mexendo sempre até engrossar. Polvilhe com canela no final.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Aveia com Maçã e Canela', '<h1>Aveia com Maçã</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena de aveia</li><li>1 maçã ralada</li><li>1 chávena de água ou leite</li><li>Canela em pó</li></ul><h3>Preparação:</h3><p>Coza a aveia com a maçã por 5 min. Junte a canela e envolva bem antes de servir.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Burrito de Pequeno-Almoço (Feijão/Veg)', '<h1>Burrito Veggie</h1><h3>Ingredientes:</h3><ul><li>1 tortilha integral</li><li>1/2 chávena de feijão preto</li><li>Pimento e cebola salteados</li><li>Abacate</li></ul><h3>Preparação:</h3><p>Aqueça a tortilha, recheie com os vegetais e o feijão. Enrole e sirva quente.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Abacate Esmagado em Pão de Centeio', '<h1>Abacate no Pão</h1><h3>Ingredientes:</h3><ul><li>2 fatias de pão de centeio</li><li>1 abacate maduro</li><li>Lima e sementes de sésamo</li></ul><h3>Preparação:</h3><p>Torre o pão. Esmague o abacate com lima e barre no pão. Finalize com sésamo.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Quinoa com Pêssegos', '<h1>Quinoa com Pêssego</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena de quinoa cozida</li><li>1 pêssego fatiado</li><li>Sementes de cânhamo</li></ul><h3>Preparação:</h3><p>Misture a quinoa cozida com o pêssego fresco e polvilhe com as sementes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Panquecas de Banana (Farinha de Aveia)', '<h1>Panquecas de Banana</h1><h3>Ingredientes:</h3><ul><li>1 banana</li><li>1/2 chávena de farinha de aveia</li><li>1/4 chávena de leite vegetal</li></ul><h3>Preparação:</h3><p>Bata tudo no liquidificador e cozinhe pequenas porções numa frigideira antiaderente.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Aveia Adormecida com Abóbora', '<h1>Aveia com Abóbora</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena aveia</li><li>1/4 chávena puré abóbora</li><li>1/2 chávena leite vegetal</li></ul><h3>Preparação:</h3><p>Misture tudo num frasco e deixe no frigorífico durante a noite. Coma fresco.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Iogurte de Soja com Frutas Vermelhas', '<h1>Iogurte com Fruta</h1><h3>Ingredientes:</h3><ul><li>1 chávena iogurte soja</li><li>Bagas frescas</li><li>Linhaça moída</li></ul><h3>Preparação:</h3><p>Coloque o iogurte numa taça e junte as bagas e a linhaça por cima.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Pão de Espelta com Manteiga de Frutos Secos', '<h1>Pão com Manteiga</h1><h3>Ingredientes:</h3><ul><li>2 fatias pão espelta</li><li>Manteiga de amêndoa</li><li>Mirtilos frescos</li></ul><h3>Preparação:</h3><p>Torre o pão e barre com a manteiga. Junte mirtilos para adoçar.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tofu and Veggie Hash', '<h1>Mexido de Tofu</h1><h3>Ingredientes:</h3><ul><li>1/2 bloco de tofu</li><li>Batata em cubos</li><li>Pimento e alho</li></ul><h3>Preparação:</h3><p>Salteie a batata e vegetais. Junte o tofu esfarelado e cozinhe 5 min.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Papas de Pera e Noz', '<h1>Papas de Pera</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena aveia</li><li>1 pera fatiada</li><li>Nozes picadas</li></ul><h3>Preparação:</h3><p>Coza a aveia no leite e sirva decorado com a pera e as nozes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Smoothie with Spinach/Fruit', '<h1>Smoothie Verde</h1><h3>Ingredientes:</h3><ul><li>Espinafres, manga e banana</li><li>1/2 chávena de água</li></ul><h3>Preparação:</h3><p>Bata todos os ingredientes até obter um batido cremoso e homogéneo.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Buckwheat with Savory Herbs', '<h1>Trigo com Ervas</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena trigo sarraceno</li><li>Salsa, coentros e tomate cherry</li></ul><h3>Preparação:</h3><p>Coza o trigo e envolva-o com as ervas aromáticas picadas e os tomates.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Fruit Salad with Hemp Seeds', '<h1>Salada com Sementes</h1><h3>Ingredientes:</h3><ul><li>Mix de frutas sazonais</li><li>Sementes de cânhamo</li></ul><h3>Preparação:</h3><p>Corte as frutas numa taça e polvilhe com as sementes de cânhamo nutritivas.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Mixed Berry Parfait (Vegan)', '<h1>Parfait Vegan</h1><h3>Ingredientes:</h3><ul><li>Iogurte de coco</li><li>Granola e bagas mistas</li></ul><h3>Preparação:</h3><p>Monte em camadas num copo: primeiro iogurte, depois fruta e por fim granola.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Steel Cut Oats with Figs', '<h1>Aveia com Figos</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena aveia em grão</li><li>Figos frescos e canela</li></ul><h3>Preparação:</h3><p>Coza a aveia lentamente (20 min). Sirva com figos fatiados e canela.</p>');");

        String s_miso = "<h1>Sopa Miso</h1><h3>Ingredientes:</h3><ul><li>Pasta de miso, tofu em cubos, algas marinhas</li><li>Caldo de legumes ou água</li></ul><h3>Preparação:</h3><p>Dissolva a pasta de miso no caldo quente (sem ferver). Junte o tofu e as algas e sirva.</p>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Sopa Miso com Tofu', '" + s_miso + "');");

        String e_salteadas = "<h1>Ervilhas Salteadas</h1><h3>Ingredientes:</h3><ul><li>Ervilhas de quebrar frescas</li><li>Sementes de sésamo, azeite, sal</li></ul><h3>Preparação:</h3><p>Salteie as ervilhas em lume forte com azeite até estarem tenras mas crocantes. Salpique com sésamo.</p>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Ervilhas de Quebrar Salteadas', '" + e_salteadas + "');");

        String abob_milho = "<h1>Curgete e Milho</h1><h3>Ingredientes:</h3><ul><li>1 curgete picada, 1 chávena de milho doce</li><li>Ervas frescas (manjericão ou salsa), azeite</li></ul><h3>Preparação:</h3><p>Salteie a curgete e o milho com um fio de azeite até dourarem. Envolva as ervas frescas picadas.</p>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Salteado de Abobrinha e Milho', '" + abob_milho + "');");

        String mix_vapor = "<h1>Mix de Vegetais ao Vapor</h1><h3>Ingredientes:</h3><ul><li>Cenoura, brócolos, couve-flor, feijão-verde</li><li>Sal e fio de azeite</li></ul><h3>Preparação:</h3><p>Coza os legumes ao vapor por 5-8 min para manter as vitaminas. Tempere com sal e azeite cru.</p>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Mistura de Vegetais ao Vapor', '" + mix_vapor + "');");

        String abob_assada = "<h1>Abóbora Assada</h1><h3>Ingredientes:</h3><ul><li>Abóbora menina em fatias</li><li>Canela em pó, sal, fio de azeite</li></ul><h3>Preparação:</h3><p>Disponha a abóbora num tabuleiro, regue com azeite e salpique com canela. Asse até caramelizar.</p>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Abóbora Menina Assada', '" + abob_assada + "');");

        // --- ENGLISH RECIPES ---
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Oatmeal with Blueberries and Walnuts', '<h1>Oatmeal</h1><p>Cook oats with milk. Top with berries and walnuts.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Whole Grain Pancakes with Fresh Fruit', '<h1>Pancakes</h1><p>Make with whole wheat flour and serve with fruit.</p>');");
        
        String tofuScrambleEn = "<h1>Tofu Scramble with Vegetables</h1><h3>Ingredients:</h3><ul><li>Firm tofu, veggies and spices</li></ul><h3>Instructions:</h3><p>Crumble tofu, sauté with veggies and spices for 5 min.</p>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tofu Scramble with Spinach', '" + tofuScrambleEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tofu and Veggie Hash', '" + tofuScrambleEn + "');");

        String generalEn = "<h1>Recipe</h1><p>Preparation details coming soon. Enjoy your healthy meal!</p>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Quinoa Salad with Roasted Vegetables', '<h1>Quinoa Salad</h1><p>Mix cooked quinoa with roasted zucchini and peppers.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Black Bean and Corn Tacos', '<h1>Black Bean Tacos</h1><p>Fill tortillas with beans, corn and avocado.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Chickpea Curry with Brown Rice', '<h1>Chickpea Curry</h1><p>Chickpeas in coconut curry sauce over rice.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Lentil and Vegetable Stew', '<h1>Lentil Stew</h1><p>Hearty lentil stew with carrots and potatoes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Barley and Mushroom Soup', '<h1>Barley Mushroom Soup</h1><p>Savory soup with pearl barley and mushrooms.</p>');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 110) {
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
