# Web Team 7
----------

## Contributors 
- Bùi Ngọc Dũng B21DCTC031
- Nguyễn Vương Đoàn B21DCTC029
- Lê Hải Đăng B21DCTC027
- Trần Văn Hùng B21DCTC047
- Chu Văn Mạnh B21DCTC068
- Nguyễn Cường Minh B21DCTC009


```mermaid
classDiagram
direction BT
class CART {
   character varying(255) CART_STATUS
   character varying CONTENT
   timestamp CREATED_AT
   numeric(38,2) SUB_TOTAL
   timestamp UPDATED_AT
   bigint USER_ID
   bigint ID
}

class CART_ITEM {
   boolean ACTIVE
   character varying CONTENT
   timestamp CREATED_AT
   numeric(38,2) DISCOUNT
   numeric(38,2) PRICE
   bigint QUANTITY
   character varying(255) SKU
   timestamp UPDATED_AT
   bigint CART_ID
   integer PRODUCT_ID
   integer ID
}
class CATEGORY {
   character varying CONTENT
   timestamp CREATED_AT
   character varying(255) META_TITLE
   character varying(255) SLUG
   character varying(255) TITLE
   timestamp UPDATED_AT
   bigint PARENT_ID
   bigint ID
}
class ORDERS {
   character varying(255) ADDRESS
   timestamp CREATED_AT
   character varying(255) FIRST_NAME
   numeric(38,2) ITEM_DISCOUNT
   character varying(255) LAST_NAME
   character varying(255) MIDDLE_NAME
   character varying(255) MOBILE
   character varying(255) STATUS
   numeric(38,2) SUB_TOTAL
   numeric(38,2) TOTAL
   timestamp UPDATED_AT
   bigint USER_ID
   bigint ID
}
class ORDER_ITEM {
   timestamp CREATED_AT
   numeric(38,2) DISCOUNT
   numeric(38,2) PRICE
   bigint QUANTITY
   timestamp UPDATED_AT
   bigint ORDER_ID
   integer PRODUCT_ID
   bigint ID
}
class PRODUCT {
   character varying CONTENT
   timestamp CREATED_AT
   numeric(38,2) DISCOUNT
   timestamp ENDS_AT
   character large object IMAGE_LINK
   character varying(255) META_TITLE
   numeric(38,2) PRICE
   timestamp PUBLISHED_AT
   bigint QUANTITY
   boolean SHOP
   character varying(255) SKU
   character varying(255) SLUG
   timestamp STARTS_AT
   character large object SUMMARY
   character varying(255) TITLE
   timestamp UPDATED_AT
   bigint USER_ID
   integer ID
}
class PRODUCT_CATEGORY {
   integer PRODUCT_ID
   bigint CATEGORY_ID
}
class PRODUCT_META {
   character large object CONTENT
   timestamp CREATED_AT
   character varying(255) KEY_PRODUCT_META
   timestamp UPDATED_AT
   integer PRODUCT_ID
   bigint ID
}
class PRODUCT_TAG {
   integer PRODUCT_ID
   bigint TAG_ID
}
class TAG {
   character varying CONTENT
   timestamp CREATED_AT
   character varying(255) META_TITLE
   character varying(255) SLUG
   character varying(255) TITLE
   timestamp UPDATED_AT
   bigint ID
}
class TOKEN {
   boolean CONFIRMED_REGISTER
   boolean EXPIRED
   boolean REVOKED
   character varying(255) TOKEN
   character varying(255) TOKEN_TYPE
   bigint USER_ID
   bigint ID
}
class TRANSACTIONS {
   timestamp CREATED_AT
   character varying(255) MODE
   character varying(255) STATUS
   character varying(255) TYPE
   timestamp UPDATED_AT
   bigint ORDER_ID
   bigint USER_ID
   bigint ID
}
class USERS {
   character varying(255) EMAIL
   boolean ENABLED
   character varying(255) FIRST_NAME
   character varying(255) LAST_NAME
   character varying(255) PASSWORD
   character varying(255) USER_ROLE
   bigint ID
}
class USER_META {
   character varying(255) ADDRESS
   date BIRTHDAY
   character varying(255) FIRST_NAME
   character varying(255) LAST_NAME
   character varying(255) MIDDLE_NAME
   character varying(255) MOBILE
   character varying(255) NICKNAME
   character varying(255) USER_META_GENDER
   bigint USER_ID
   bigint ID
}

CART  -->  USERS 
CART_ITEM  -->  CART 
CART_ITEM  -->  PRODUCT 
CATEGORY  -->  CATEGORY 
ORDERS  -->  USERS 
ORDER_ITEM  -->  ORDERS 
ORDER_ITEM  -->  PRODUCT 
PRODUCT  -->  USERS 
PRODUCT_CATEGORY  -->  CATEGORY 
PRODUCT_CATEGORY  -->  PRODUCT 
PRODUCT_META  -->  PRODUCT 
PRODUCT_TAG  -->  PRODUCT 
PRODUCT_TAG  -->  TAG 
TOKEN  -->  USERS 
TRANSACTIONS  -->  ORDERS 
TRANSACTIONS  -->  USERS 
USER_META  -->  USERS 

```