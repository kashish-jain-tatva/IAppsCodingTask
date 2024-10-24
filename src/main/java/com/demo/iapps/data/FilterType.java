package com.demo.iapps.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FilterType {

	WIDTH("width"), HEIGHT("height"), DPI("dpi"), NEWSPAPERNAME("newspapername");

	private final String type;

}
