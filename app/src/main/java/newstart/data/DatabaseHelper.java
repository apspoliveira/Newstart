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
    private static final int DATABASE_VERSION = 131;

    // Table settings
    private static final String COL_S_INDEX = "settings_index";
    private static final String TABLE_S_LANG = "settings_lang";
    private static final String COL_S_INDEX_L = "settings_index";

    // Table recipes/articles
    private static final String TABLE_RECIPES = "recipes";
    private static final String COL_RECIPE_TITLE = "title";
    private static final String COL_RECIPE_CONTENT = "content";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_S_LANG + " (" + COL_S_INDEX_L + " INTEGER PRIMARY KEY, language TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_RECIPES + " (" + COL_RECIPE_TITLE + " TEXT PRIMARY KEY, " + COL_RECIPE_CONTENT + " TEXT);");

        insertInitialRecipes(sqLiteDatabase);

        // Define o idioma padrão como Português ('pt')
        sqLiteDatabase.execSQL("INSERT OR IGNORE INTO " + TABLE_S_LANG + " VALUES(1, 'pt');");
    }

    private void insertInitialRecipes(SQLiteDatabase db) {
        // --- RECEITAS EM PORTUGUÊS ---

        // Pequenos-almoços
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_AVEIA_MIRTILOS + "', '<h1>Aveia com Mirtilos e Nozes</h1><h3>Ingredientes:</h3><ul><li>1 chávena de flocos de aveia integrais</li><li>2 chávenas de água (ou leite/bebida vegetal)</li><li>½ chávena de mirtilos frescos ou congelados</li><li>2 colheres de sopa de nozes picadas</li><li>1 colher de chá de canela</li></ul><h3>Preparação:</h3><p>Coloque a aveia e o líquido numa panela pequena e leve ao lume médio. Cozinhe por 5 minutos, mexendo sempre, até engrossar. Retire do lume, misture metade dos mirtilos para que derretam e libertem a sua cor, e decore o topo com o resto dos mirtilos, as nozes picadas e a canela.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PANQUECAS_INTEGRAIS + "', '<h1>Panquecas Integrais</h1><h3>Ingredientes:</h3><ul><li>1 xícara farinha integral</li><li>1 banana madura</li><li>Leite vegetal</li></ul><h3>Instruções:</h3><p>Misture tudo e cozinhe numa frigideira antiaderente.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SMOOTHIE_LINHACA + "', '<h1>Smoothie de Frutas</h1><h3>Ingredientes:</h3><ul><li>1 banana</li><li>1 xícara de bagas mistas</li><li>1 c. sopa de linhaça moída</li><li>250ml de leite vegetal</li></ul><h3>Preparação:</h3><p>Bata todos os ingredientes no liquidificador até ficar cremoso.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PAPA_SARRACENO + "', '<h1>Papa de Trigo Sarraceno</h1><h3>Ingredientes:</h3><ul><li>1/2 xícara trigo sarraceno</li><li>1.5 xícaras de leite vegetal</li><li>Amêndoas torradas</li></ul><h3>Preparação:</h3><p>Cozinhe o trigo no leite por 20 min em lume brando. Sirva com as amêndoas por cima.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PUDIM_CHIA + "', '<h1>Pudim de Chia</h1><h3>Ingredientes:</h3><ul><li>3 c. sopa de sementes de chia</li><li>1 xícara de leite de coco</li><li>1/2 manga fresca picada</li></ul><h3>Preparação:</h3><p>Misture a chia com o leite. Deixe repousar no frigorífico por 4h. Sirva com a manga.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_TORRADA_ABACATE + "', '<h1>Torrada com Abacate</h1><h3>Ingredientes:</h3><ul><li>2 fatias de pão integral</li><li>1 abacate maduro</li><li>Sal, pimenta e limão</li></ul><h3>Preparação:</h3><p>Torre o pão. Esmague o abacate com um pouco de sumo de limão e espalhe sobre o pão.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_TORRADA_HUMUS + "', '<h1>Torrada com Húmus</h1><h3>Ingredientes:</h3><ul><li>2 fatias de pão integral</li><li>4 c. sopa de húmus</li><li>Rodelas de pepino</li></ul><h3>Preparação:</h3><p>Torre o pão, barre com o húmus e decore com o pepino fresco.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PAINCO_CAJU + "', '<h1>Painço com Tâmaras</h1><h3>Ingredientes:</h3><ul><li>1/2 xícara de painço</li><li>2 xícaras de leite vegetal</li><li>Tâmaras e cajus picados</li></ul><h3>Preparação:</h3><p>Cozinhe o painço no leite (20 min). Adicione as tâmaras e os cajus no final.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_TACA_ACAI + "', '<h1>Taça de Açaí</h1><h3>Ingredientes:</h3><ul><li>200g polpa de açaí congelada</li><li>1 banana madura</li><li>1/2 chávena de granola</li><li>Frutas frescas</li></ul><h3>Preparação:</h3><p>Bata o açaí e a banana até ficar cremoso. Coloque numa taça e decore com granola e fruta.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_QUINOA_PEQUENO_ALMOCO + "', '<h1>Quinoa com Bagas</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena de quinoa cozida</li><li>1/2 chávena de leite vegetal</li><li>Mix de bagas e nozes</li><li>1 colher de sopa de ácer</li></ul><h3>Preparação:</h3><p>Aqueça a quinoa no leite vegetal. Sirva com as bagas e nozes por cima.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_TORRADA_AMENDOIM_BANANA + "', '<h1>Torrada com Banana</h1><h3>Ingredientes:</h3><ul><li>2 fatias de pão integral</li><li>2 colheres de manteiga de amendoim</li><li>1 banana fatiada</li><li>Sementes de chia</li></ul><h3>Preparação:</h3><p>Torre o pão, barre com a manteiga de amendoim e disponha a banana. Polvilhe com chia.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SMOOTHIE_BOWL_FRUTOS_SECOS + "', '<h1>Smoothie Bowl</h1><h3>Ingredientes:</h3><ul><li>Manga e banana congeladas</li><li>1/2 chávena de iogurte vegetal</li><li>Mix de nozes e sementes</li></ul><h3>Preparação:</h3><p>Bata a fruta com o iogurte até ficar espesso. Sirva numa taça com os frutos secos.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PAPAS_MILHO + "', '<h1>Papas de Milho</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena de sêmola de milho</li><li>2 chávenas de leite vegetal</li><li>Pitada de sal e canela</li></ul><h3>Preparação:</h3><p>Coza o milho no leite mexendo sempre até engrossar. Polvilhe com canela no final.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_AVEIA_MACA_CANELA + "', '<h1>Aveia com Maçã</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena de aveia</li><li>1 maçã ralada</li><li>1 chávena de água ou leite</li><li>Canela em pó</li></ul><h3>Preparação:</h3><p>Coza a aveia com a maçã por 5 min. Junte a canela e envolva bem antes de servir.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BURRITO_VEG + "', '<h1>Burrito Veggie</h1><h3>Ingredientes:</h3><ul><li>1 tortilha integral</li><li>1/2 chávena de feijão preto</li><li>Pimento e cebola salteados</li><li>Abacate</li></ul><h3>Preparação:</h3><p>Aqueça a tortilha, recheie com os vegetais e o feijão. Enrole e sirva quente.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_ABACATE_CENTEIO + "', '<h1>Abacate no Pão</h1><h3>Ingredientes:</h3><ul><li>2 fatias de pão de centeio</li><li>1 abacate maduro</li><li>Lima e sementes de sésamo</li></ul><h3>Preparação:</h3><p>Torre o pão. Esmague o abacate com lima e barre no pão. Finalize com sésamo.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_QUINOA_PESSEGOS + "', '<h1>Quinoa com Pêssego</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena de quinoa cozida</li><li>1 pêssego fatiado</li><li>Sementes de cânhamo</li></ul><h3>Preparação:</h3><p>Misture a quinoa cozida com o pêssego fresco e polvilhe com as sementes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PANQUECAS_BANANA + "', '<h1>Panquecas de Banana</h1><h3>Ingredientes:</h3><ul><li>1 banana</li><li>1/2 chávena de farinha de aveia</li><li>1/4 chávena de leite vegetal</li></ul><h3>Preparação:</h3><p>Bata tudo no liquidificador e cozinhe pequenas porções numa frigideira antiaderente.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_AVEIA_ABOBORA + "', '<h1>Aveia com Abóbora</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena aveia</li><li>1/4 chávena puré abóbora</li><li>1/2 chávena leite vegetal</li></ul><h3>Preparação:</h3><p>Misture tudo num frasco e deixe no frigorífico durante a noite. Coma fresco.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_IOGURTE_SOJA_FRUTAS + "', '<h1>Iogurte com Fruta</h1><h3>Ingredientes:</h3><ul><li>1 chávena iogurte soja</li><li>Bagas frescas</li><li>Linhaça moída</li></ul><h3>Preparação:</h3><p>Coloque o iogurte numa taça e junte as bagas e a linhaça por cima.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PAO_ESPELTA_MANTEIGA + "', '<h1>Pão com Manteiga</h1><h3>Ingredientes:</h3><ul><li>2 fatias pão espelta</li><li>Manteiga de amêndoa</li><li>Mirtilos frescos</li></ul><h3>Preparação:</h3><p>Torre o pão e barre com a mantesiga. Junte mirtilos para adoçar.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_MEXIDO_TOFU_VEGETAIS + "', '<h1>Mexido de Tofu</h1><h3>Ingredientes:</h3><ul><li>1/2 bloco de tofu</li><li>Batata em cubos</li><li>Pimento e alho</li></ul><h3>Preparação:</h3><p>Salteie a batata e vegetais. Junte o tofu esfarelado e cozinhe 5 min.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PAPAS_PERA_NOZ + "', '<h1>Papas de Pera</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena aveia</li><li>1 pera fatiada</li><li>Nozes picadas</li></ul><h3>Preparação:</h3><p>Coza a aveia no leite e sirva decorado com a pera e as nozes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SMOOTHIE_ESPINAFRES + "', '<h1>Smoothie Verde</h1><h3>Ingredientes:</h3><ul><li>Espinafres, manga e banana</li><li>1/2 chávena de água</li></ul><h3>Preparação:</h3><p>Bata todos os ingredientes até obter um batido cremoso e homogéneo.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SARRACENO_ERVAS + "', '<h1>Trigo com Ervas</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena trigo sarraceno</li><li>Salsa, coentros e tomate cherry</li></ul><h3>Preparação:</h3><p>Coza o trigo e envolva-o com as ervas aromáticas picadas e os tomates.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALADA_FRUTA_CANHAMO + "', '<h1>Salada com Sementes</h1><h3>Ingredientes:</h3><ul><li>Mix de frutas sazonais</li><li>Sementes de cânhamo</li></ul><h3>Preparação:</h3><p>Corte as frutas numa taça e polvilhe com as sementes de cânhamo nutritivas.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PARFAIT_VEGAN + "', '<h1>Parfait Vegan</h1><h3>Ingredientes:</h3><ul><li>Iogurte de coco</li><li>Granola e bagas mistas</li></ul><h3>Preparação:</h3><p>Monte em camadas num copo: primeiro iogurte, depois fruta e por fim granola.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_AVEIA_FIGOS + "', '<h1>Aveia com Figos</h1><h3>Ingredientes:</h3><ul><li>1/2 chávena aveia em grão</li><li>Figos frescos e canela</li></ul><h3>Preparação:</h3><p>Coza a aveia lentamente (20 min). Sirva com figos fatiados e canela.</p>');");

        String tofuScramblePtFull = "<h1>Mexido de Tofu com Vegetais</h1><h3>Ingredientes:</h3><ul><li>1 bloco de tofu firme</li><li>1 colher de sopa de azeite</li><li>Vegetais: cebola, alho, espinafres, cogumelos</li><li>1/2 c. chá de açafrão, salt e pimenta</li></ul><h3>Preparação:</h3><ol><li>Esmague o tofu. Salteie os vegetais no azeite.</li><li>Junte o tofu e temperos. Cozinhe por 5 min mexendo sempre.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_MEXIDO_TOFU_ESPINAFRES + "', '" + tofuScramblePtFull + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_MEXIDO_TOFU_VEGETAIS + "', '" + tofuScramblePtFull + "');");

        // Almoços e Jantares (PT)
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALADA_QUINOA_LEGUMES + "', '<h1>Salada de Quinoa</h1><h3>Ingredientes:</h3><ul><li>1 chávena de quinoa</li><li>Beringela, curgete e pimento em cubos</li><li>Azeite e limão</li></ul><h3>Preparação:</h3><p>Asse os legumes a 200°C. Misture com a quinoa cozida e tempere com limão.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_TACOS_FEIJAO_MILHO + "', '<h1>Tacos de Feijão</h1><h3>Ingredientes:</h3><ul><li>Tortilhas de milho</li><li>Feijão preto, milho, abacate</li></ul><h3>Preparação:</h3><p>Aqueça o feijão com cominhos. Recheie as tortilhas com os ingredientes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_CARIL_GRAO_BICO + "', '<h1>Caril de Grão</h1><h3>Ingredientes:</h3><ul><li>Grão cozido, leite de coco, caril e arroz</li></ul><h3>Preparação:</h3><p>Refogue cebola e caril. Junte o grão e leite de coco. Cozinhe 10 min e sirva com arroz.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_WRAP_HUMUS + "', '<h1>Wrap de Húmus</h1><h3>Ingredientes:</h3><ul><li>Tortilha, húmus e vegetais frescos</li></ul><h3>Preparação:</h3><p>Barre o húmus na tortilha e recheie com vegetais crocantes. Enrole.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_CHILI_BATATA_DOCE + "', '<h1>Chili de Batata-Doce</h1><h3>Ingredientes:</h3><ul><li>Batata-doces, feijão preto e tomate</li></ul><h3>Preparação:</h3><p>Cozinhe tudo num tacho com especiarias até a batata estar macia.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_GISADO_LENTILHAS + "', '<h1>Gisado de Lentilhas</h1><h3>Ingredientes:</h3><ul><li>Lentilhas, cenoura e batata</li></ul><h3>Preparação:</h3><p>Cozinhe em caldo de legumes até as lentilhas estarem macias.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_ZOODLES_PESTO + "', '<h1>Zoodles com Pesto</h1><h3>Ingredientes:</h3><ul><li>Curgete em espirais e pesto vegan</li></ul><h3>Preparação:</h3><p>Salteie a curgete por 2 min e envolva no molho pesto.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BOWL_QUINOA_FEIJAO + "', '<h1>Bowl de Quinoa</h1><h3>Ingredientes:</h3><ul><li>Quinoa, feijão, milho e abacate</li></ul><h3>Preparação:</h3><p>Misture os ingredientes numa taça e tempere com lima.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALADA_FARRO + "', '<h1>Salada de Farro</h1><h3>Ingredientes:</h3><ul><li>Farro cozido, arandos e nozes</li></ul><h3>Preparação:</h3><p>Misture os ingredientes e tempere com vinagrete.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_MASSA_LENTILHA_MARINARA + "', '<h1>Massa de Lentilha</h1><h3>Ingredientes:</h3><ul><li>Massa de lentilha, molho tomate, manjericão</li></ul><h3>Preparação:</h3><p>Coza a massa e envolva no molho de tomate temperado.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_EMPADAO_LENTILHA + "', '<h1>Empadão de Lentilha</h1><h3>Ingredientes:</h3><ul><li>Lentilhas estufadas e puré de batata</li></ul><h3>Preparação:</h3><p>Cubra as lentilhas com puré e leve ao forno a dourar.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BOWL_BUDDHA_GRAO + "', '<h1>Bowl Buddha</h1><h3>Ingredientes:</h3><ul><li>Grão assado, quinoa e espinafres, molho de sésamo</li></ul><h3>Preparação:</h3><p>Disponha os ingredientes numa taça e regue com o molho.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SOPA_CEVADA_VEGETAIS + "', '<h1>Sopa de Cevada</h1><h3>Ingredientes:</h3><ul><li>Cevada perlada, cenoura, aipo, batata</li></ul><h3>Preparação:</h3><p>Coza todos os ingredientes picados num caldo de legumes aromático.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_WRAP_FALAFEL + "', '<h1>Wrap de Falafel</h1><h3>Ingredientes:</h3><ul><li>Falafel, tortilha, húmus e salada</li></ul><h3>Preparação:</h3><p>Aqueça a tortilha, barre com húmus, coloque falafel e salada. Enrole.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SOPA_ERVILHA + "', '<h1>Sopa de Ervilha</h1><h3>Ingredientes:</h3><ul><li>Ervilhas secas, cebola, louro</li></ul><h3>Preparação:</h3><p>Coza as ervilhas com o refogado até ficarem macias e triture.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_TABULE_SALSA + "', '<h1>Tabule</h1><h3>Ingredientes:</h3><ul><li>Bulgur, muita salsa, hortelã, tomate</li></ul><h3>Preparação:</h3><p>Hidrate o bulgur e misture com os vegetais e ervas picadas.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SOPA_MINESTRONE + "', '<h1>Sopa Minestrone</h1><h3>Ingredientes:</h3><ul><li>Feijão, massa, tomate, legumes variados</li></ul><h3>Preparação:</h3><p>Coza os legumes com o tomate. Junte a massa e o feijão no final.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALADA_FEIJAO_FRADE + "', '<h1>Salada de Feijão Frade</h1><h3>Ingredientes:</h3><ul><li>Feijão frade, cebola, salsa, azeite</li></ul><h3>Preparação:</h3><p>Misture o feijão com a cebola e salsa picadas. Tempere bem.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PAELLA_VEGETAIS + "', '<h1>Paella de Vegetais</h1><h3>Ingredientes:</h3><ul><li>Arroz, açafrão, pimento, ervilhas</li></ul><h3>Preparação:</h3><p>Cozinhe o arroz com o açafrão e envolva os legumes salteados.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SOPA_REPOLHO_BATATAS + "', '<h1>Sopa de Repolho</h1><h3>Ingredientes:</h3><ul><li>Repolho, batata, cebola, alho</li></ul><h3>Preparação:</h3><p>Coza tudo num caldo simples e termine com um fio de azeite.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALADA_GRAO_MEDITERRANEA + "', '<h1>Salada de Grão</h1><h3>Ingredientes:</h3><ul><li>Grão cozido, tomate, pepino, azeitonas</li></ul><h3>Preparação:</h3><p>Misture tudo numa taça e tempere com azeite e limão.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_KORMA_VEGETAIS + "', '<h1>Korma de Vegetais</h1><h3>Ingredientes:</h3><ul><li>Mix de vegetais, leite de coco, especiarias</li></ul><h3>Preparação:</h3><p>Estufe os vegetais no leite de coco with as especiarias até amolecerem.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SOPA_FEIJAO_BRANCO + "', '<h1>Sopa de Feijão Branco e Couve</h1><h3>Ingredientes:</h3><ul><li>Feijão branco, couve, alho</li></ul><h3>Preparação:</h3><p>Coza o feijão e junte a couve picada nos últimos minutos.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_LENTILHA_ARROZ_MUJADARA + "', '<h1>Mujadara</h1><h3>Ingredientes:</h3><ul><li>Lentilhas verdes, arroz integral, cebola frita</li></ul><h3>Preparação:</h3><p>Coza o arroz com as lentilhas. Cubra com cebola caramelizada.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALADA_GRAO_ASSADO + "', '<h1>Salada de Grão Assado</h1><h3>Ingredientes:</h3><ul><li>Grão assado crocante, mix de folhas</li></ul><h3>Preparação:</h3><p>Adicione o grão assado por cima de uma salada verde fresca.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_CHILI_TRES_FEIJOES + "', '<h1>Three Bean Chili</h1><h3>Ingredientes:</h3><ul><li>Feijão preto, vermelho e branco</li><li>Polpa de tomate e especiarias</li></ul><h3>Preparação:</h3><p>Cozinhe os feijões no molho de tomate bem temperado.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SUSHI_VEGGIE + "', '<h1>Sushi Veggie</h1><h3>Ingredientes:</h3><ul><li>Arroz integral, folhas nori, vegetais</li></ul><h3>Preparação:</h3><p>Enrole o arroz e vegetais na alga e corte.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_GISADO_TOMATE_LENTILHAS + "', '<h1>Gisado de Tomate</h1><h3>Ingredientes:</h3><ul><li>Lentilhas vermelhas, tomate pelado</li></ul><h3>Preparação:</h3><p>Cozinhe as lentilhas diretamente no molho de tomate temperado.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_QUINOA_ROMA + "', '<h1>Quinoa com Romã</h1><h3>Ingredientes:</h3><ul><li>Quinoa cozida, romã, hortelã</li></ul><h3>Preparação:</h3><p>Misture a quinoa com os bagos de romã e tempere com limão.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SOPA_CEVADA_COGUMELOS + "', '<h1>Sopa de Cevada e Cogumelos</h1><h3>Ingredientes:</h3><ul><li>Cevada perlada e mix de cogumelos</li></ul><h3>Preparação:</h3><p>Coza a cevada com os cogumelos picados num caldo de legumes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_TOFU_BROCOLIS_AMENDOIM + "', '<h1>Tofu com Amendoim</h1><h3>Ingredientes:</h3><ul><li>Tofu, brócolos e manteiga de amendoim</li></ul><h3>Preparação:</h3><p>Grelhe o tofu e envolva tudo no molho de amendoim fluido.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BOWL_QUINOA_MEXICANO + "', '<h1>Mexican Bowl</h1><h3>Ingredientes:</h3><ul><li>Quinoa, feijão, milho, salsa</li></ul><h3>Preparação:</h3><p>Combine os ingredientes numa taça e envolva no molho salsa.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BROCOLIS_TOFU_GRELHADO + "', '<h1>Brócolis e Tofu</h1><h3>Ingredientes:</h3><ul><li>Brócolis, tofu firme, alho</li></ul><h3>Preparação:</h3><p>Coza os brócolis ao vapor e sirva com tofu grelhado.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALADA_VERDE_SEMENTES + "', '<h1>Salada com Sementes</h1><h3>Ingredientes:</h3><ul><li>Mix de folhas, sementes variadas</li></ul><h3>Preparação:</h3><p>Misture as folhas e junte sementes tostadas no topo.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALTEADO_TEMPEH + "', '<h1>Salteado de Tempeh</h1><h3>Ingredientes:</h3><ul><li>Tempeh, vegetais variados, soja</li></ul><h3>Preparação:</h3><p>Salteie tudo em lume forte com molho de soja e gengibre.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_COUVE_FLOR_TAHINI + "', '<h1>Couve-Flor com Tahini</h1><h3>Ingredientes:</h3><ul><li>Couve-flor, molho sésamo, limão</li></ul><h3>Preparação:</h3><p>Asse a couve-flor e cubra com o molho de tahine.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BATATA_DOCE_FOLHAS_VERDES + "', '<h1>Batata-Doce com Verdes</h1><h3>Ingredientes:</h3><ul><li>Batata-doce, espinafres, alho</li></ul><h3>Preparação:</h3><p>Asse a batata e recheie com os verdes salteados.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_RAIZES_ASSADAS_ALHO + "', '<h1>Raízes com Alho</h1><h3>Ingredientes:</h3><ul><li>Cenoura, beterraba, molho alho</li></ul><h3>Preparação:</h3><p>Asse as raízes e sirva com o molho fresco de alho.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALADA_COUVE_QUINOA + "', '<h1>Salada de Couve</h1><h3>Ingredientes:</h3><ul><li>Couve kale, quinoa, nozes</li></ul><h3>Preparação:</h3><p>Misture a couve massajada com quinoa e nozes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_ESPARGOS_ASSADOS + "', '<h1>Espargos com Amêndoas</h1><h3>Ingredientes:</h3><ul><li>Espargos, amêndoas, azeite</li></ul><h3>Preparação:</h3><p>Asse os espargos e salpique com as amêndoas.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PIMENTOS_RECHEADOS + "', '<h1>Pimentos Recheados</h1><h3>Ingredientes:</h3><ul><li>Pimentos, arroz selvagem, ervas</li></ul><h3>Preparação:</h3><p>Recheie os pimentos e leve ao forno até dourarem.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_RISOTO_COGUMELOS + "', '<h1>Risoto de Cogumelos</h1><h3>Ingredientes:</h3><ul><li>Arroz integral, cogumelos, caldo</li></ul><h3>Preparação:</h3><p>Cozinhe o arroz lentamente com os cogumelos e caldo.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_LASANHA_BERINGELA + "', '<h1>Lasanha de Beringela</h1><h3>Ingredientes:</h3><ul><li>Beringela, tomate, creme caju</li></ul><h3>Preparação:</h3><p>Faça camadas de beringela e molho e leve a assar.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_TOFU_AGRIDOCE + "', '<h1>Tofu Agridoce</h1><h3>Ingredientes:</h3><ul><li>Tofu, pimentos e molho agridoce</li></ul><h3>Preparação:</h3><p>Frite o tofu e envolva no molho agridoce.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_COUVE_BRUXELAS_BALSAMICO + "', '<h1>Bruxelas com Balsâmico</h1><h3>Ingredientes:</h3><ul><li>Couves bruxelas, vinagre balsâmico</li></ul><h3>Preparação:</h3><p>Asse as couves e regue com balsâmico.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BOK_CHOY_TEMPEH + "', '<h1>Bok Choy Tempeh</h1><h3>Ingredientes:</h3><ul><li>Bok choy, tempeh, sésamo</li></ul><h3>Preparação:</h3><p>Salteie com alho e gengibre e finalize com sésamo.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_ABOBORA_ASSADA_QUINOA + "', '<h1>Abóbora com Quinoa</h1><h3>Ingredientes:</h3><ul><li>Abóbora, quinoa, nozes</li></ul><h3>Preparação:</h3><p>Recheie a abóbora assada com a quinoa temperada.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BIFES_PORTOBELLO + "', '<h1>Bifes Portobello</h1><h3>Ingredientes:</h3><ul><li>Portobello, alho, ervas</li></ul><h3>Preparação:</h3><p>Grelhe os cogumelos inteiros com alho e ervas.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BIFES_COUVE_FLOR + "', '<h1>Bifes de Couve-Flor</h1><h3>Ingredientes:</h3><ul><li>Couve-flor, especiarias</li></ul><h3>Preparação:</h3><p>Asse fatias grossas de couve-flor bem temperadas no forno.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALTEADO_BROCOLIS_CAJU + "', '<h1>Salteado com Caju</h1><h3>Ingredientes:</h3><ul><li>Brócolis, cajus, soja</li></ul><h3>Preparação:</h3><p>Salteie os vegetais e junte os cajus no final.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BETERRABA_GLACIADA + "', '<h1>Beterraba Glaciada</h1><h3>Ingredientes:</h3><ul><li>Beterraba, balsâmico</li></ul><h3>Preparação:</h3><p>Glacie a beterraba cozida com a redução de vinagre.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALADA_REPOLHO_CENOURA + "', '<h1>Salada de Repolho</h1><h3>Ingredientes:</h3><ul><li>Repolho, cenoura, molho</li></ul><h3>Preparação:</h3><p>Misture tudo com um molho leve de limão.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_ALCACHOFRAS_VAPOR + "', '<h1>Alcachofras</h1><h3>Ingredientes:</h3><ul><li>Alcachofras, limão</li></ul><h3>Preparação:</h3><p>Coza ao vapor e sirva com vinagrete.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PALITOS_BATATA_DOCE + "', '<h1>Palitos de Batata</h1><h3>Ingredientes:</h3><ul><li>Batata-doce, páprica</li></ul><h3>Preparação:</h3><p>Asse os palitos até ficarem dourados e crocantes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_VAGEM_ALHO + "', '<h1>Vagem com Alho</h1><h3>Ingredientes:</h3><ul><li>Vagem, alho, azeite</li></ul><h3>Preparação:</h3><p>Salteie a vagem cozida com muito alho.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SOPA_MISO_TOFU + "', '<h1>Sopa Miso</h1><h3>Ingredientes:</h3><ul><li>Miso, tofu, algas</li></ul><h3>Preparação:</h3><p>Prepare o caldo de miso e junte o tofu em cubos.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_ERVILHAS_QUEBRAR_SALTEADAS + "', '<h1>Ervilhas Salteadas</h1><h3>Ingredientes:</h3><ul><li>Ervilhas, sésamo</li></ul><h3>Preparação:</h3><p>Salteie rapidamente em lume forte.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALTEADO_ABOBORINHA_MILHO + "', '<h1>Curgete e Milho</h1><h3>Ingredientes:</h3><ul><li>Curgete, milho, ervas</li></ul><h3>Preparação:</h3><p>Salteie os dois com ervas frescas.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_VEGETAIS_VAPOR + "', '<h1>Mix de Vegetais</h1><h3>Ingredientes:</h3><ul><li>Legumes variados</li></ul><h3>Preparação:</h3><p>Coza ao vapor para manter os nutrientes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_ABOBORA_MENINA_ASSADA + "', '<h1>Abóbora Assada</h1><h3>Ingredientes:</h3><ul><li>Abóbora, canela</li></ul><h3>Preparação:</h3><p>Asse com um fio de azeite e sal até caramelizar.</p>');");
        
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_ACELGA_ALHO + "', '<h1>Acelga Salteada com Alho</h1>" +
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

        // --- RECEITAS EM INGLÊS ---
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
        if (oldVersion < 131) {
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
