package quanli.duan.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeImage {
    BANNER_IMAGE("BANNER_IMAGE"),
    PRODUCT_IMAGE("PRODUCT_IMAGE"),
    BRAND_IMAGE("BRAND_IMAGE");

    private final String value;
}
