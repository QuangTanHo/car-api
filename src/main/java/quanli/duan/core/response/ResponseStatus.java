package quanli.duan.core.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    SUCCESSFUL("000", "Successful"),

    // authorized & validate request
    UNAUTHORIZED("001", "Unauthorized"),
    EMAIL_EXIST("002", "Email exists"),
    INVALID_REQUEST_PARAMETER("003", "Invalid request parameter: "),
    USER_NOT_FOUND("004", "User not found"),
    PHONE_NUMBER_EXIST("005", "Phone number exists"),
    BRAND_NOT_FOUND("006", "Brand not found"),
    BRAND_NAME_EXIST("007", "Brand name exists"),
    CAR_NAME_EXIST("008", "Car name exists"),
    CAR_NOT_FOUND("009", "Product not found"),

    FILE_NOT_FOUND("010", "File not found"),
    UPLOAD_FILE_FAIL("011", "Upload file fail"),
    ATTRIBUTE_NOT_FOUND("012", "Attribute not found"),
    ATTRIBUTE_NAME_EXIST("013", "Attribute name exists"),
    PRODUCT_CODE_EXIST("014", "Product code exists"),
    BAR_CODE_EXIST("015", "Bar code exists"),
    PRODUCT_IMAGE_NOT_FOUND("016", "Product image not found"),
    ARTICLE_NAME_EXIST("017", "Article name exists"),
    SLUG_EXIST("018", "Slug exists"),
    ARTICLE_NOT_FOUND("019", "Article not found"),
    PRODUCT_ORDER_NOT_FOUND("020", "Product order not found"),
    PRODUCT_ORDER_DETAIL_NOT_FOUND("021", "Product order detail not found"),
    CATEGORY_DISPLAY_EXIST("022", "Category display exists"),
    VOUCHER_NOT_FOUND("023", "Voucher not found"),
    VOUCHER_CODE_EXIST("024", "Voucher code exists"),
    VOUCHER_EXPIRED("025", "Voucher expired"),
    VOUCHER_OUT_OF_STOCK("026", "Voucher out of stock"),
    VOUCHER_DATE_INVALID("027", "Voucher date invalid"),
    FLASH_SALE_DATE_INVALID("028", "Flash sale date invalid"),
    INTERNAL_SERVER_ERROR("999", "Internal server error");

    private final String code;
    private final String message;

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";

    public static ResponseStatus findResponseStatus(String code) {
        for (ResponseStatus v : values()) {
            if (v.getCode().equals(code)) {
                return v;
            }
        }

        return null;
    }
}
