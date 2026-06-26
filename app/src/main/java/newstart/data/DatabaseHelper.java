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
    private static final int DATABASE_VERSION = 143;

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
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_AVEIA_MIRTILOS + "', '<h3>Ingredientes:</h3><ul><li>1/2 chávena de flocos de aveia</li><li>1 chávena de leite (ou bebida vegetal, ex: amêndoa ou aveia)</li><li>1/2 chávena de mirtilos frescos ou congelados</li><li>2 colheres de sopa de nozes picadas</li><li>Opcional: 1 colher de chá de mel, xarope de ácer ou canela</li></ul><h3>Preparação (Papas Quentes):</h3><ol><li><b>Ferver o leite:</b> Numa panela pequena, coloque o leite a lume brando.</li><li><b>Juntar a aveia:</b> Adicione os flocos de aveia e a canela. Mexa frequentemente.</li><li><b>Cozinhar:</b> Deixe cozinhar durante 3 a 4 minutos até que a aveia absorva a maior parte do líquido e ganhe uma textura cremosa.</li><li><b>Servir:</b> Retire do lume, transfira para uma taça e adicione os mirtilos e as nozes por cima. Adoce a gosto.</li></ol>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PANQUECAS_INTEGRAIS + "', '<h3>Ingredientes (Base):</h3><p>Para preparar cerca de 6 a 8 panquecas, vai precisar de:</p><ul><li>1 xícara (chá) de farinha de trigo integral ou farinha de aveia</li><li>1 xícara (chá) de leite ou bebida vegetal (ex: amêndoa, soja ou aveia)</li><li>1 ovo médio</li><li>1 colher (sopa) de azeite ou óleo de coco (opcional)</li><li>1 colher (chá) de fermento em pó</li><li>1 pitada de sal</li></ul><h3>Ingredientes extra (opcionais):</h3><p>1 colher de sopa de mel ou açúcar mascavado (para panquecas doces), essência de baunilha ou canela.</p><h3>Preparação:</h3><ol><li><b>Misturar os ingredientes:</b> Coloque todos os ingredientes no liquidificador (ou numa taça funda, utilizando uma vara de arames).</li><li><b>Bater:</b> Bata até obter uma massa macia e homogénea. A massa integral pode parecer um pouco mais líquida que a tradicional, mas não adicione mais farinha para manter a textura leve.</li><li><b>Descansar:</b> Deixe a massa repousar durante 10 a 15 minutos. Isso ajuda a farinha integral a absorver os líquidos e melhora a consistência final.</li><li><b>Cozinhar:</b> Aqueça uma frigideira antiaderente em lume médio e unte-a com um fio de azeite ou manteiga (espalhe com papel absorvente para retirar o excesso).</li><li><b>Dourar:</b> Verta cerca de 1/4 de xícara da massa no centro da frigideira. Deixe cozinhar até começarem a surgir pequenas bolhas na superfície (cerca de 1 a 2 minutos).</li><li><b>Virar:</b> Vire a panqueca com uma espátula e deixe cozinhar do outro lado até ficar dourada.</li></ol>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SMOOTHIE_LINHACA + "', '<h3>Ingredientes:</h3><ul><li>1/2 chávena de frutos vermelhos congelados (morangos, amoras ou framboesas)</li><li>1 banana madura</li><li>100 ml de iogurte natural ou iogurte vegetal</li><li>100 ml de bebida vegetal (ex: amêndoa) ou água de coco</li><li>1 colher de sopa de flocos de aveia ou sementes de chia (opcional, para saciedade)</li><li>Mel ou xarope de ácer a gosto (opcional)</li></ul><h3>Preparação:</h3><ol><li><b>Ingredientes líquidos:</b> Coloque os ingredientes líquidos no fundo do liquidificador (iogurte e bebida vegetal/água) para ajudar as lâminas a rodar mais facilmente.</li><li><b>Adicionar sólidos:</b> Adicione os sólidos, colocando a banana cortada em pedaços e os frutos congelados por cima.</li><li><b>Triturar:</b> Triture na potência máxima durante 1 a 2 minutos até obter uma mistura espessa, cremosa e homogénea.</li><li><b>Ajustar consistência:</b> Se estiver muito espesso, adicione um pouco mais de líquido. Se estiver muito líquido, adicione mais fruta congelada ou gelo.</li><li><b>Servir:</b> Sirva de imediato num copo alto.</li></ol>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PAPA_SARRACENO + "', '<h3>Ingredientes (para 2 doses):</h3><ul><li><b>Trigo sarraceno:</b> 1/2 chávena de farinha ou flocos de trigo sarraceno</li><li><b>Líquido:</b> 2 chávenas de leite vegetal (como amêndoa, aveia ou coco)</li><li><b>Amêndoas:</b> 30 g de amêndoas (laminadas, picadas ou em manteiga)</li><li><b>Adoçante:</b> 1 colher de sopa de xarope de tâmaras ou xarope de ácer (opcional)</li><li><b>Topping:</b> Fruta fresca fatiada (banana, morangos ou pêssego) e canela em pó a gosto</li></ul><h3>Preparação:</h3><ol><li><b>Misturar:</b> Num tacho, coloque a farinha ou os flocos de trigo sarraceno juntamente com o leite vegetal.</li><li><b>Cozinhar:</b> Leve a lume brando, mexendo sempre para não ganhar grumos. Deixe cozinhar durante cerca de 5 a 10 minutos, até obter a textura de papa cremosa desejada.</li><li><b>Aromatizar:</b> Adicione o xarope (ou outro adoçante) e uma pitada de canela.</li><li><b>Tostar as amêndoas:</b> Numa frigideira à parte e sem óleo, toste ligeiramente as amêndoas laminadas até ficarem douradas e perfumadas.</li><li><b>Servir:</b> Verta as papas para uma taça, disponha a fruta por cima e polvilhe com as amêndoas tostadas.</li></ol>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PUDIM_CHIA + "', '<h3>Ingredientes (para 1 a 2 doses):</h3><ul><li>1 manga madura (ou cerca de 170g a 200g)</li><li>200ml de leite de coco (ou iogurte natural / bebida vegetal)</li><li>2 a 3 colheres de sopa de sementes de chia</li><li>1 colher de sopa de mel, xarope de ácer ou adoçante a gosto (opcional)</li><li><b>Toppings:</b> coco ralado, pedaços de manga ou hortelã (opcional)</li></ul><h3>Preparação:</h3><ol><li><b>Preparar o puré:</b> Descasque a manga e corte-a em pedaços. Coloque cerca de metade da manga num liquidificador ou processador de alimentos e triture até obter um creme liso e homogénea.</li><li><b>Misturar:</b> Vire o puré de manga para uma taça ou frasco e adicione o leite de coco (ou iogurte) e o adoçante. Misture bem.</li><li><b>Adicionar a chia:</b> Junte as sementes de chia ao preparado e envolva tudo muito bem com um garfo ou fouet.</li><li><b>Deixar repousar:</b> Tape o recipiente e leve ao frigorífico durante, pelo menos, 4 horas ou (idealmente) durante a noite para que a chia hidrate e ganhe consistência de pudim.</li><li><b>Servir:</b> Antes de comer, corte a restante metade da manga em cubos e coloque por cima do pudim. Finalize com coco ralado.</li></ol>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_TORRADA_ABACATE + "', '<h3>Ingredientes:</h3><ul><li>2 fatias de pão integral</li><li>1 abacate maduro</li><li>Sal, pimenta e limão</li></ul><h3>Preparação:</h3><p>Torre o pão. Esmague o abacate com um pouco de sumo de limão e espalhe sobre o pão.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_TORRADA_HUMUS + "', '<h3>Ingredientes:</h3><ul><li>2 fatias de pão integral</li><li>4 c. sopa de húmus</li><li>Rodelas de pepino</li></ul><h3>Preparação:</h3><p>Torre o pão, barre com o húmus e decore com o pepino fresco.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PAINCO_CAJU + "', '<h3>Ingredientes:</h3><ul><li>1/2 xícara de painço</li><li>2 xícaras de leite vegetal</li><li>Tâmaras e cajus picados</li></ul><h3>Preparação:</h3><p>Cozinhe o painço no leite (20 min). Adicione as tâmaras e os cajus no final.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_TACA_ACAI + "', '<h3>Ingredientes:</h3><ul><li>200g polpa de açaí congelada</li><li>1 banana madura</li><li>1/2 chávena de granola</li><li>Frutas frescas</li></ul><h3>Preparação:</h3><p>Bata o açaí e a banana até ficar cremoso. Coloque numa taça e decore com granola e fruta.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_QUINOA_PEQUENO_ALMOCO + "', '<h3>Ingredientes:</h3><ul><li>1/2 chávena de quinoa cozida</li><li>1/2 chávena de leite vegetal</li><li>Mix de bagas e nozes</li><li>1 colher de sopa de ácer</li></ul><h3>Preparação:</h3><p>Aqueça a quinoa no leite vegetal. Sirva com as bagas e nozes por cima.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_TORRADA_AMENDOIM_BANANA + "', '<h3>Ingredientes:</h3><ul><li>2 fatias de pão integral</li><li>2 colheres de manteiga de amendoim</li><li>1 banana fatiada</li><li>Sementes de chia</li></ul><h3>Preparação:</h3><p>Torre o pão, barre com a manteiga de amendoim e disponha a banana. Polvilhe com chia.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SMOOTHIE_BOWL_FRUTOS_SECOS + "', '<h3>Ingredientes:</h3><ul><li>Manga e banana congeladas</li><li>1/2 chávena de iogurte vegetal</li><li>Mix de nozes e sementes</li></ul><h3>Preparação:</h3><p>Bata a fruta com o iogurte até ficar espesso. Sirva numa taça com os frutos secos.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PAPAS_MILHO + "', '<h3>Ingredientes:</h3><ul><li>1/2 chávena de sêmola de milho</li><li>2 chávenas de leite vegetal</li><li>Pitada de sal e canela</li></ul><h3>Preparação:</h3><p>Coza o milho no leite mexendo sempre até engrossar. Polvilhe com canela no final.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_AVEIA_MACA_CANELA + "', '<h3>Ingredientes:</h3><ul><li>1/2 chávena de aveia</li><li>1 maçã ralada</li><li>1 chávena de água ou leite</li><li>Canela em pó</li></ul><h3>Preparação:</h3><p>Coza a aveia com a maçã por 5 min. Junte a canela e envolva bem antes de servir.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BURRITO_VEG + "', '<h3>Ingredientes:</h3><ul><li>1 tortilha integral</li><li>1/2 chávena de feijão preto</li><li>Pimento e cebola salteados</li><li>Abacate</li></ul><h3>Preparação:</h3><p>Aqueça a tortilha, recheie com os vegetais e o feijão. Enrole e sirva quente.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_ABACATE_CENTEIO + "', '<h3>Ingredientes:</h3><ul><li>2 fatias de pão de centeio</li><li>1 abacate maduro</li><li>Lima e sementes de sésamo</li></ul><h3>Preparação:</h3><p>Torre o pão. Esmague o abacate com lima e barre no pão. Finalize com sésamo.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_QUINOA_PESSEGOS + "', '<h3>Ingredientes:</h3><ul><li>1/2 chávena de quinoa cozida</li><li>1 pêssego fatiado</li><li>Sementes de cânhamo</li></ul><h3>Preparação:</h3><p>Misture a quinoa cozida com o pêssego fresco e polvilhe com as sementes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PANQUECAS_BANANA + "', '<h3>Ingredientes:</h3><ul><li>1 banana</li><li>1/2 chávena de farinha de aveia</li><li>1/4 chávena de leite vegetal</li></ul><h3>Preparação:</h3><p>Bata tudo no liquidificador e cozinhe pequenas porções numa frigideira antiaderente.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_AVEIA_ABOBORA + "', '<h3>Ingredientes:</h3><ul><li>1/2 chávena aveia</li><li>1/4 chávena puré abóbora</li><li>1/2 chávena leite vegetal</li></ul><h3>Preparação:</h3><p>Misture tudo num frasco e deixe no frigorífico durante a noite. Coma fresco.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_IOGURTE_SOJA_FRUTAS + "', '<h3>Ingredientes:</h3><ul><li>1 chávena iogurte soja</li><li>Bagas frescas</li><li>Linhaça moída</li></ul><h3>Preparação:</h3><p>Coloque o iogurte numa taça e junte as bagas e a linhaça por cima.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PAO_ESPELTA_MANTEIGA + "', '<h3>Ingredientes:</h3><ul><li>2 fatias pão espelta</li><li>Manteiga de amêndoa</li><li>Mirtilos frescos</li></ul><h3>Preparação:</h3><p>Torre o pão e barre com a mantesiga. Junte mirtilos para adoçar.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_MEXIDO_TOFU_VEGETAIS + "', '<h3>Ingredientes:</h3><ul><li>1/2 bloco de tofu</li><li>Batata em cubos</li><li>Pimento e alho</li></ul><h3>Preparação:</h3><p>Salteie a batata e vegetais. Junte o tofu esfarelado e cozinhe 5 min.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PAPAS_PERA_NOZ + "', '<h3>Ingredientes:</h3><ul><li>1/2 chávena aveia</li><li>1 pera fatiada</li><li>Nozes picadas</li></ul><h3>Preparação:</h3><p>Coza a aveia no leite e sirva decorado com a pera e as nozes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SMOOTHIE_ESPINAFRES + "', '<h3>Ingredientes:</h3><ul><li>Espinafres, manga e banana</li><li>1/2 chávena de água</li></ul><h3>Preparação:</h3><ol><li>Bata todos os ingredientes até obter um batido cremoso e homogéneo.</li></ol>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SARRACENO_ERVAS + "', '<h3>Ingredientes:</h3><ul><li>1/2 chávena trigo sarraceno</li><li>Salsa, coentros e tomate cherry</li></ul><h3>Preparação:</h3><p>Coza o trigo e envolva-o com as ervas aromáticas picadas e os tomates.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALADA_FRUTA_CANHAMO + "', '<h3>Ingredientes:</h3><ul><li>Mix de frutas sazonais</li><li>Sementes de cânhamo</li></ul><h3>Preparação:</h3><p>Corte as frutas numa taça e polvilhe com as sementes de cânhamo nutritivas.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PARFAIT_VEGAN + "', '<h3>Ingredientes:</h3><ul><li>Iogurte de coco</li><li>Granola e bagas mistas</li></ul><h3>Preparação:</h3><p>Monte em camadas num copo: primeiro iogurte, depois fruta e por fim granola.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_AVEIA_FIGOS + "', '<h3>Ingredientes:</h3><ul><li>1/2 chávena aveia em grão</li><li>Figos frescos e canela</li></ul><h3>Preparação:</h3><p>Coza a aveia lentamente (20 min). Sirva com figos fatiados e canela.</p>');");

        String tofuScramblePtFull = "<h3>Ingredientes:</h3><ul><li>1 bloco de tofu firme</li><li>1 colher de sopa de azeite</li><li>Vegetais: cebola, alho, espinafres, cogumelos</li><li>1/2 c. chá de açafrão, salt e pimenta</li></ul><h3>Preparação:</h3><ol><li>Esmague o tofu. Salteie os vegetais no azeite.</li><li>Junte o tofu e temperos. Cozinhe por 5 min mexendo sempre.</li></ol>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_MEXIDO_TOFU_ESPINAFRES + "', '" + tofuScramblePtFull + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_MEXIDO_TOFU_VEGETAIS + "', '" + tofuScramblePtFull + "');");

        // Almoços e Jantares (PT)
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALADA_QUINOA_LEGUMES + "', '<h3>Ingredientes:</h3><ul><li>1 chávena de quinoa</li><li>Beringela, curgete e pimento em cubos</li><li>Azeite e limão</li></ul><h3>Preparação:</h3><p>Asse os legumes a 200°C. Misture com a quinoa cozida e tempere com limão.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_TACOS_FEIJAO_MILHO + "', '<h3>Ingredientes:</h3><ul><li>Tortilhas de milho</li><li>Feijão preto, milho, abacate</li></ul><h3>Preparação:</h3><p>Aqueça o feijão com cominhos. Recheie as tortilhas com os ingredientes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_CARIL_GRAO_BICO + "', '<h3>Ingredientes:</h3><ul><li>Grão cozido, leite de coco, caril e arroz</li></ul><h3>Preparação:</h3><p>Refogue cebola e caril. Junte o grão e leite de coco. Cozinhe 10 min e sirva com arroz.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_WRAP_HUMUS + "', '<h3>Ingredientes:</h3><ul><li>Tortilha, húmus e vegetais frescos</li></ul><h3>Preparação:</h3><p>Barre o húmus na tortilha e recheie com vegetais crocantes. Enrole.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_CHILI_BATATA_DOCE + "', '<h3>Ingredientes:</h3><ul><li>Batata-doces, feijão preto e tomate</li></ul><h3>Preparação:</h3><p>Cozinhe tudo num tacho com especiarias até a batata estar macia.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_GISADO_LENTILHAS + "', '<h3>Ingredientes:</h3><ul><li>Lentilhas, cenoura e batata</li></ul><h3>Preparação:</h3><p>Cozinhe em caldo de legumes até as lentilhas estarem macias.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_ZOODLES_PESTO + "', '<h3>Ingredientes:</h3><ul><li>Curgete em espirais e pesto vegan</li></ul><h3>Preparação:</h3><p>Salteie a curgete por 2 min e envolva no molho pesto.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BOWL_QUINOA_FEIJAO + "', '<h3>Ingredientes:</h3><ul><li>Quinoa, feijão, milho e abacate</li></ul><h3>Preparação:</h3><p>Misture os ingredientes numa taça e tempere com lima.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALADA_FARRO + "', '<h3>Ingredientes:</h3><ul><li>Farro cozido, arandos e nozes</li></ul><h3>Preparação:</h3><p>Misture os ingredientes e tempere com vinagrete.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_MASSA_LENTILHA_MARINARA + "', '<h3>Ingredientes:</h3><ul><li>Massa de lentilha, molho tomate, manjericão</li></ul><h3>Preparação:</h3><p>Coza a massa e envolva no molho de tomate temperado.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_EMPADAO_LENTILHA + "', '<h3>Ingredientes:</h3><ul><li>Lentilhas estufadas e puré de batata</li></ul><h3>Preparação:</h3><p>Cubra as lentilhas com puré e leve ao forno a dourar.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BOWL_BUDDHA_GRAO + "', '<h3>Ingredientes:</h3><ul><li>Grão assado, quinoa e espinafres, molho de sésamo</li></ul><h3>Preparação:</h3><p>Disponha os ingredientes numa taça e regue com o molho.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SOPA_CEVADA_VEGETAGES + "', '<h3>Ingredientes:</h3><ul><li>Cevada perlada, cenoura, aipo, batata</li></ul><h3>Preparação:</h3><p>Coza todos os ingredientes picados num caldo de legumes aromático.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_WRAP_FALAFEL + "', '<h3>Ingredientes:</h3><ul><li>Falafel, tortilha, húmus e salada</li></ul><h3>Preparação:</h3><p>Aqueça a tortilha, barre com húmus, coloque falafel e salada. Enrole.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SOPA_ERVILHA + "', '<h3>Ingredientes:</h3><ul><li>Ervilhas secas, cebola, louro</li></ul><h3>Preparação:</h3><p>Coza as ervilhas com o refogado até ficarem macias e triture.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_TABULE_SALSA + "', '<h3>Ingredientes:</h3><ul><li>Bulgur, muita salsa, hortelã, tomate</li></ul><h3>Preparação:</h3><p>Hidrate o bulgur e misture com os vegetais e ervas picadas.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SOPA_MINESTRONE + "', '<h3>Ingredientes:</h3><ul><li>Feijão, massa, tomate, legumes variados</li></ul><h3>Preparação:</h3><p>Coza os legumes com o tomate. Junte a massa e o feijão no final.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALADA_FEIJAO_FRADE + "', '<h3>Ingredientes:</h3><ul><li>Feijão frade, cebola, salsa, azeite</li></ul><h3>Preparação:</h3><p>Misture o feijão com a cebola e salsa picadas. Tempere bem.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PAELLA_VEGETAIS + "', '<h3>Ingredientes:</h3><ul><li>Arroz, açafrão, pimento, ervilhas</li></ul><h3>Preparação:</h3><p>Cozinhe o arroz com o açafrão e envolva os legumes salteados.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SOPA_REPOLHO_BATATAS + "', '<h3>Ingredientes:</h3><ul><li>Repolho, batata, cebola, alho</li></ul><h3>Preparação:</h3><p>Coza tudo num caldo simples e termine com um fio de azeite.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALADA_GRAO_MEDITERRANEA + "', '<h3>Ingredientes:</h3><ul><li>Grão cozido, tomate, pepino, azeitonas</li></ul><h3>Preparação:</h3><p>Misture tudo numa taça e tempere com azeite e limão.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_KORMA_VEGETAIS + "', '<h3>Ingredientes:</h3><ul><li>Mix de vegetais, leite de coco, especiarias</li></ul><h3>Preparação:</h3><p>Estufe os vegetais no leite de coco com as especiarias até amolecerem.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SOPA_FEIJAO_BRANCO + "', '<h3>Ingredientes:</h3><ul><li>Feijão branco, couve, alho</li></ul><h3>Preparação:</h3><p>Coza o feijão e junte a couve picada nos últimos minutos.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_LENTILHA_ARROZ_MUJADARA + "', '<h3>Ingredientes:</h3><ul><li>Lentilhas verdes, arroz integral, cebola frita</li></ul><h3>Preparação:</h3><p>Coza o arroz com as lentilhas. Cubra com cebola caramelizada.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALADA_GRAO_ASSADO + "', '<h3>Ingredientes:</h3><ul><li>Grão assado crocante, mix de folhas</li></ul><h3>Preparação:</h3><p>Adicione o grão assado por cima de uma salada verde fresca.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_CHILI_TRES_FEIJOES + "', '<h3>Ingredientes:</h3><ul><li>Feijão preto, vermelho e branco</li><li>Polpa de tomate e especiarias</li></ul><h3>Preparação:</h3><p>Cozinhe os feijões no molho de tomate bem temperado.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SUSHI_VEGGIE + "', '<h3>Ingredientes:</h3><ul><li>Arroz integral, folhas nori, vegetais</li></ul><h3>Preparação:</h3><p>Enrole o arroz e vegetais na alga e corte.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_GISADO_TOMATE_LENTILHAS + "', '<h3>Ingredientes:</h3><ul><li>Lentilhas vermelhas, tomate pelado</li></ul><h3>Preparação:</h3><p>Cozinhe as lentilhas diretamente no molho de tomate temperado.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_QUINOA_ROMA + "', '<h3>Ingredientes:</h3><ul><li>Quinoa cozida, romã, hortä</li></ul><h3>Preparação:</h3><p>Misture a quinoa com os bagos de romã e tempere com limão.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SOPA_CEVADA_COGUMELOS + "', '<h3>Ingredientes:</h3><ul><li>Cevada perlada e mix de cogumelos</li></ul><h3>Preparação:</h3><p>Coza a cevada com os cogumelos picados num caldo de legumes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_TOFU_BROCOLIS_AMENDOIM + "', '<h3>Ingredientes:</h3><ul><li>Tofu, brócolos e manteiga de amendoim</li></ul><h3>Preparação:</h3><p>Grelhe o tofu e envolva tudo no molho de amendoim fluido.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BOWL_QUINOA_MEXICANO + "', '<h3>Ingredientes:</h3><ul><li>Quinoa, feijão, milho, salsa</li></ul><h3>Preparação:</h3><p>Combine os ingredientes numa taça e envolva no molho salsa.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BROCOLIS_TOFU_GRELHADO + "', '<h3>Ingredientes:</h3><ul><li>Brócolis, tofu firme, alho</li></ul><h3>Preparação:</h3><p>Coza os brócolis ao vapor e sirva com tofu grelhado.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALADA_VERDE_SEMENTES + "', '<h3>Ingredientes:</h3><ul><li>Mix de folhas, sementes variadas</li></ul><h3>Preparação:</h3><p>Misture as folhas e junte sementes tostadas no topo.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALTEADO_TEMPEH + "', '<h3>Ingredientes:</h3><ul><li>Tempeh, vegetais variados, soja</li></ul><h3>Preparação:</h3><p>Salteie tudo em lume forte com molho de soja e gengibre.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_COUVE_FLOR_TAHINI + "', '<h3>Ingredientes:</h3><ul><li>Couve-flor, molho sésamo, limão</li></ul><h3>Preparação:</h3><p>Asse a couve-flor e cubra com o molho de tahine.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BATATA_DOCE_FOLHAS_VERDES + "', '<h3>Ingredientes:</h3><ul><li>Batata-doce, espinafres, alho</li></ul><h3>Preparação:</h3><p>Asse a batata e recheie com os verdes salteados.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_RAIZES_ASSADAS_ALHO + "', '<h3>Ingredientes:</h3><ul><li>Cenoura, beterraba, molho alho</li></ul><h3>Preparação:</h3><p>Asse as raízes e sirva com o molho fresco de alho.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALADA_COUVE_QUINOA + "', '<h3>Ingredientes:</h3><ul><li>Couve kale, quinoa, nozes</li></ul><h3>Preparação:</h3><p>Misture a couve massajada com quinoa e nozes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_ESPARGOS_ASSADOS + "', '<h3>Ingredientes:</h3><ul><li>Espargos, amêndoas, azeite</li></ul><h3>Preparação:</h3><p>Asse os espargos e salpique com as amêndoas.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PIMENTOS_RECHEADOS + "', '<h3>Ingredientes:</h3><ul><li>Pimentos, arroz selvagem, ervas</li></ul><h3>Preparação:</h3><p>Recheie os pimentos e leve ao forno até dourarem.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_RISOTO_COGUMELOS + "', '<h3>Ingredientes:</h3><ul><li>Arroz integral, cogumelos, caldo</li></ul><h3>Preparação:</h3><p>Cozinhe o arroz lentamente com os cogumelos e caldo.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_LASANHA_BERINGELA + "', '<h3>Ingredientes:</h3><ul><li>Beringela, tomate, creme caju</li></ul><h3>Preparação:</h3><p>Faça camadas de beringela e molho e leve a assar.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_TOFU_AGRIDOCE + "', '<h3>Ingredientes:</h3><ul><li>Tofu, pimentos e molho agridoce</li></ul><h3>Preparação:</h3><p>Frite o tofu e envolva no molho agridoce.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_COUVE_BRUXELAS_BALSAMICO + "', '<h3>Ingredientes:</h3><ul><li>Couves bruxelas, vinagre balsâmico</li></ul><h3>Preparação:</h3><p>Asse as couves e regue com balsâmico.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BOK_CHOY_TEMPEH + "', '<h3>Ingredientes:</h3><ul><li>Bok choy, tempé, sésamo</li></ul><h3>Preparação:</h3><p>Salteie com alho e gengibre e finalize com sésamo.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_ABOBORA_ASSADA_QUINOA + "', '<h3>Ingredientes:</h3><ul><li>Abóbora, quinoa, nozes</li></ul><h3>Preparação:</h3><p>Recheie a abóbora assada com a quinoa temperada.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BIFES_PORTOBELLO + "', '<h3>Ingredientes:</h3><ul><li>Portobello, alho, ervas</li></ul><h3>Preparação:</h3><p>Grelhe os cogumelos inteiros com alho e ervas.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BIFES_COUVE_FLOR + "', '<h3>Ingredientes:</h3><ul><li>Couve-flor, especiarias</li></ul><h3>Preparação:</h3><p>Asse fatias grossas de couve-flor bem temperadas no forno.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALTEADO_BROCOLIS_CAJU + "', '<h3>Ingredientes:</h3><ul><li>Brócolis, cajus, soja</li></ul><h3>Preparação:</h3><p>Salteie os vegetais e junte os cajus no final.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_BETERRABA_GLACIADA + "', '<h3>Ingredientes:</h3><ul><li>Beterraba, balsâmico</li></ul><h3>Preparação:</h3><p>Glacie a beterraba cozida com a redução de vinagre.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALADA_REPOLHO_CENOURA + "', '<h3>Ingredientes:</h3><ul><li>Repolho, cenoura, molho</li></ul><h3>Preparação:</h3><p>Misture tudo com um molho leve de limão.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_ALCACHOFRAS_VAPOR + "', '<h3>Ingredientes:</h3><ul><li>Alcachofras, limão</li></ul><h3>Preparação:</h3><p>Coza ao vapor e sirva com vinagrete.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_PALITOS_BATATA_DOCE + "', '<h3>Ingredientes:</h3><ul><li>Batata-doce, páprica</li></ul><h3>Preparação:</h3><p>Asse os palitos até ficarem dourados e crocantes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_VAGEM_ALHO + "', '<h3>Ingredientes:</h3><ul><li>Vagem, alho, azeite</li></ul><h3>Preparação:</h3><p>Salteie a vagem cozida com muito alho.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SOPA_MISO_TOFU + "', '<h3>Ingredientes:</h3><ul><li>Miso, tofu, algas</li></ul><h3>Preparação:</h3><p>Prepare o caldo de miso e junte o tofu em cubos.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_ERVILHAS_QUEBRAR_SALTEADAS + "', '<h3>Ingredientes:</h3><ul><li>Ervilhas, sésamo</li></ul><h3>Preparação:</h3><p>Salteie rapidamente em lume forte.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_SALTEADO_ABOBORINHA_MILHO + "', '<h3>Ingredientes:</h3><ul><li>Curgete, milho, ervas</li></ul><h3>Preparação:</h3><p>Salteie os dois com ervas frescas.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_VEGETAIS_VAPOR + "', '<h3>Ingredientes:</h3><ul><li>Legumes variados</li></ul><h3>Preparação:</h3><p>Coza ao vapor para manter os nutrientes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_ABOBORA_MENINA_ASSADA + "', '<h3>Ingredientes:</h3><ul><li>Abóbora, canela</li></ul><h3>Preparação:</h3><p>Asse com um fio de azeite e sal até caramelizar.</p>');");
        
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('" + MealConstants.PT_ACELGA_ALHO + "', '<h3>Ingredientes:</h3><ul>" +
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
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Oatmeal with Blueberries and Walnuts', '<p>Cook oats with milk. Top with berries and walnuts.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Whole Grain Pancakes with Fresh Fruit', '<p>Make with whole wheat flour and serve with fruit.</p>');");
        
        String tofuScrambleEn = "<h3>Ingredients:</h3><ul><li>Firm tofu, veggies and spices</li></ul><h3>Instructions:</h3><p>Crumble tofu, sauté with veggies and spices for 5 min.</p>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tofu Scramble with Spinach', '" + tofuScrambleEn + "');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Tofu and Veggie Hash', '" + tofuScrambleEn + "');");

        String generalEn = "<h3>Recipe</h3><p>Preparation details coming soon. Enjoy your healthy meal!</p>";
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Quinoa Salad with Roasted Vegetables', '<p>Mix cooked quinoa with roasted zucchini and peppers.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Black Bean and Corn Tacos', '<p>Fill tortillas with beans, corn and avocado.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Chickpea Curry with Brown Rice', '<p>Chickpeas in coconut curry sauce over rice.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Lentil and Vegetable Stew', '<p>Hearty lentil stew with carrots and potatoes.</p>');");
        db.execSQL("INSERT OR REPLACE INTO " + TABLE_RECIPES + " VALUES('Barley and Mushroom Soup', '<p>Savory soup with pearl barley and mushrooms.</p>');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 143) {
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
