package com.example.groceryappdemo.app

class Endpoints {

    companion object {

        private const val URL_LOGIN = "auth/login"
        private const val URL_REGISTER = "auth/register"
        private const val URL_CATEGORY = "category"
        private const val URL_ADDRESS = "address"
        private const val URL_SUB_CATEGORY = "subcategory/"
        private const val URL_PRODUCTS_BY_SUB_ID = "products/sub/"


        fun getLogin(): String {
            return Config.BASE_URL + URL_LOGIN
        }

        fun getRegister(): String {
            return Config.BASE_URL + URL_REGISTER

        }
        fun getCategory(): String {
            return Config.BASE_URL + URL_CATEGORY

        }
        fun saveAddress() : String {
            return Config.BASE_URL + URL_ADDRESS
        }

        fun getSubCategoryByCatId(catId: Int): String {
            return "${Config.BASE_URL + URL_SUB_CATEGORY}/$catId"
        }

        fun getProductBySubId(subId: Int): String {
            return "${Config.BASE_URL + URL_PRODUCTS_BY_SUB_ID}$subId"
        }

        fun getAddress(): String {
            return "${Config.BASE_URL + URL_ADDRESS}"
        }

        fun getAddressByID(id: String): String {
            return "${Config.BASE_URL + URL_ADDRESS}/$id"
        }
    }
}