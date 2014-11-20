package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class MyDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1007, "de.handler.mobile.android.fairmondo.datasource.database");
        addProductCategoryRelation(schema);
        addSearchSuggestions(schema);
        new DaoGenerator().generateAll(schema, args[0]);
    }

    private static void addSearchSuggestions(Schema schema) {
        Entity searchSuggestion = schema.addEntity("SearchSuggestion");
        searchSuggestion.addIdProperty();
        searchSuggestion.addStringProperty("suggest_text_1");
        searchSuggestion.addStringProperty("suggest_text_2");
        searchSuggestion.setSkipGeneration(true);
    }

    private static void addProductCategoryRelation(Schema schema) {

        Entity category = schema.addEntity("Category");
        category.addLongProperty("id").primaryKey();
        category.addStringProperty("name");
        category.addStringProperty("desc");
        category.addIntProperty("parent_id");
        category.addIntProperty("lft");
        category.addIntProperty("rgt");
        category.addIntProperty("depth");
        category.addIntProperty("children_count");
        category.addIntProperty("weight");
        category.addIntProperty("view_columns");
        category.addStringProperty("slug");

        category.setHasKeepSections(true);
        category.implementsInterface("android.os.Parcelable");
        category.setSkipGeneration(true);

        Entity product = schema.addEntity("Product");
        product.addIdProperty();
        product.addStringProperty("slug");
        product.addStringProperty("titleImageUrl");
        product.addStringProperty("htmlUrl");
        product.addStringProperty("title");
        product.addIntProperty("priceCents");
        product.addStringProperty("tagCondition");
        product.addBooleanProperty("tagFair");
        product.addBooleanProperty("tagEcologic");
        product.addBooleanProperty("tagSmallAndPrecious");
        product.addBooleanProperty("tagBorrowable");
        product.addBooleanProperty("tagSwappable");
        product.addStringProperty("sellerNickname");
        product.addBooleanProperty("sellerLegalEntity");
        product.addStringProperty("sellerHtmlUrl");
        product.addStringProperty("donation");

        product.setHasKeepSections(true);
        product.implementsInterface("android.os.Parcelable");

        /**
         * Define n:m relationship
         * using a join table
         * product 1:n productCategory n:1 category
         */
        Entity productCategory = schema.addEntity("ProductCategory");
        productCategory.setTableName("ProductCategory");
        productCategory.addIdProperty();

        productCategory.setHasKeepSections(true);
        productCategory.implementsInterface("android.os.Parcelable");
        productCategory.setSkipGeneration(true);

        // n:m relationship
        Property productCategoryDate = productCategory.addDateProperty("date").getProperty();

        Property productId = productCategory.addLongProperty("productId").notNull().getProperty();
        productCategory.addToOne(product, productId);

        Property categoryId = productCategory.addLongProperty("categoryId").notNull().getProperty();
        productCategory.addToOne(category, categoryId);

        ToMany productToProductCategories = product.addToMany(productCategory, productId);
        productToProductCategories.setName("productCategories");
        productToProductCategories.orderAsc(productCategoryDate);

        ToMany categoryToProductCategories = category.addToMany(productCategory, categoryId);
        categoryToProductCategories.setName("productCategories");
        categoryToProductCategories.orderAsc(productCategoryDate);


    }
}
