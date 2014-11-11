package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class MyDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1003, "de.handler.mobile.android.shopprototype.database");
        addProductCategoryRelation(schema);
        new DaoGenerator().generateAll(schema, args[0]);
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

        Entity product = schema.addEntity("Product");
        product.addIdProperty();
        product.addStringProperty("title");
        product.addStringProperty("content");
        product.addDateProperty("created_at").notNull();
        product.addDateProperty("updated_at").notNull();
        product.addIntProperty("user_id");
        product.addStringProperty("condition");
        product.addIntProperty("price_cents");
        product.addStringProperty("currency");
        product.addBooleanProperty("fair");
        product.addStringProperty("fair_kind");
        product.addStringProperty("fair_seal");
        product.addBooleanProperty("ecologic");
        product.addStringProperty("ecologic_seal");
        product.addBooleanProperty("small_and_precious");
        product.addStringProperty("small_and_precious_reason");
        product.addBooleanProperty("small_and_precious_handmade");
        product.addIntProperty("quantity");
        product.addStringProperty("transport_details");
        product.addStringProperty("payment_details");
        product.addIntProperty("friendly_percent");
        product.addIntProperty("calculated_fair_cents").notNull();
        product.addIntProperty("calculated_friendly_cents").notNull();
        product.addIntProperty("calculated_fee_cents").notNull();
        product.addStringProperty("condition_extra");
        product.addBooleanProperty("small_and_precious_eu_small_enterprise");
        product.addStringProperty("ecologic_kind");
        product.addStringProperty("upcycling_reason");
        product.addStringProperty("slug");
        product.addBooleanProperty("transport_pickup");
        product.addBooleanProperty("transport_type1");
        product.addBooleanProperty("transport_type2");
        product.addStringProperty("transport_type1_provider");
        product.addStringProperty("transport_type2_provider");
        product.addIntProperty("transport_type1_price_cents").notNull();
        product.addIntProperty("transport_type2_price_cents").notNull();
        product.addBooleanProperty("payment_bank_transfer");
        product.addBooleanProperty("payment_cash");
        product.addBooleanProperty("payment_paypal");
        product.addBooleanProperty("payment_cash_on_delivery");
        product.addBooleanProperty("payment_invoice");
        product.addIntProperty("payment_cash_on_delivery_price_cents").notNull();
        product.addIntProperty("basic_price_cents");
        product.addStringProperty("basic_price_amount");
        product.addStringProperty("state");
        product.addIntProperty("vat");
        product.addStringProperty("custom_seller_identifier");
        product.addStringProperty("gtin");
        product.addIntProperty("transport_type1_number");
        product.addIntProperty("transport_type2_number");
        product.addIntProperty("discount_id");
        product.addIntProperty("friendly_percent_organisation_id");
        product.addStringProperty("article_template_name");
        product.addStringProperty("transport_time");
        product.addIntProperty("quantity_available");
        product.addBooleanProperty("unified_transport");
        product.addBooleanProperty("swappable");
        product.addBooleanProperty("borrowable");
        product.addIntProperty("comments_count");
        product.addIntProperty("original_id");

        product.setHasKeepSections(true);
        product.implementsInterface("android.os.Parcelable");
        product.setSkipGeneration(true);

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
