package com.marcin.jasi.roadmemorizer.general.common.data;

import java.util.List;

public interface DataMapper<From, To> {

    To transform(From from);

    List<To> transform(List<From> from);

}
