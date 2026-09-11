# Walkthrough - Atualização de Imagens de Almoço (Janeiro)

Concluí o mapeamento das imagens ilustrativas para todas as 31 receitas de almoço de Janeiro. Agora, tanto os pequenos-almoços como os almoços de Janeiro possuem fotografias personalizadas no cartão da refeição.

## Alterações Realizadas

### [Recursos e Assets]

#### [RENOMEAR] [Imagens](file:///C:/Users/apspo/Igreja/Newstart/app/src/main/res/drawable/)
Renomeei todos os ficheiros de imagem na pasta `res/drawable` para minúsculas, garantindo a conformidade com as regras do sistema de recursos do Android.
- Ex: `PT_SALADA_QUINOA_LEGUMES.webp` -> `pt_salada_quinoa_legumes.webp`
- Ex: `PT_TACOS_FEIJAO_MILHO.jpg` -> `pt_tacos_feijao_milho.jpg`
- (Processados todos os ficheiros existentes no diretório)

### [Módulo de Nutrição]

#### [MODIFICAR] [Fragment_Nutrition.java](file:///C:/Users/apspo/Igreja/Newstart/app/src/main/java/newstart/fragments/Fragment_Nutrition.java)
Atualizei a estrutura de dados `mealImages` para incluir o mapeamento dos 31 almoços de Janeiro:

```java
mealImages.put(MealConstants.PT_SALADA_QUINOA_LEGUMES, R.drawable.pt_salada_quinoa_legumes);
mealImages.put(MealConstants.PT_TACOS_FEIJAO_MILHO, R.drawable.pt_tacos_feijao_milho);
// ... e mais 29 mapeamentos
```

## Verificações Realizadas

### Testes Automatizados
- **Build do Projeto**: O comando `gradlew app:assembleDebug` foi executado com sucesso. Todos os novos recursos foram indexados e estão disponíveis para a aplicação.

### Notas Técnicas
- **Consistência de Nomes**: O mapeamento no código utiliza agora os nomes de recursos em minúsculas, evitando erros de compilação.
- **Renderização Dinâmica**: O cartão de almoço (`includeLunch`) agora deteta automaticamente a imagem correta com base na sugestão do dia.

## Resultado Visual
Ao selecionar qualquer dia de Janeiro, o ecrã de Nutrição apresenta agora imagens apetecíveis tanto para o pequeno-almoço como para o almoço, melhorando significativamente a estética e a utilidade da aplicação.
