package com.epam.esm.exception;

public final class LocalizationExceptionColumn {
    public static final String USER_ID_NULL = "user_id_null";
    public static final String USER_ID_LESS_1 = "user_id_less_1";
    public static final String GIFT_CERTIFICATE_ID_LESS_1 = "gift_certificate_id_less_1";
    public static final String ORDER_ID_LESS_1 = "order_id_less_1";
    public static final String NAME_NULL = "name_null";
    public static final String NAME_LENGTH_ERROR = "name_length_error";
    public static final String DESCRIPTION_NULL = "description_null";
    public static final String DESCRIPTION_LENGTH_ERROR = "description_length_error";
    public static final String PRICE_ERROR = "price_less_0.01";
    public static final String DURATION_ERROR = "duration_error";
    public static final String TAG_NAME_ERROR = "tag_name_length_error";
    public static final String GIFT_CERTIFICATE_NAME_LENGTH_ERROR = "gift_certificate_name_length_error";
    public static final String ORDER_SORT_BY_NAME_PARAM_ERROR = "order_by_name";
    public static final String ORDER_SORT_BY_CREATE_DATE_PARAM_ERROR = "order_by_create_date";
    public static final String ID_LESS_1 = "id_less_1";
    public static final String PAGE_NUMBER_LESS_1 = "page_number_less_1";
    public static final String PAGE_NUMBER_GREATER_999 = "page_number_greater_999";
    public static final String PAGE_SIZE_LESS_1 = "page_size_less_1";
    public static final String PAGE_SIZE_GREATER_10 = "page_size_greater_10";

    private LocalizationExceptionColumn() {}
}
