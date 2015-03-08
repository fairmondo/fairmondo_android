package de.handler.mobile.android.fairmondo.datalayer.businessobject;

import org.modelmapper.ModelMapper;

import java.util.List;

import de.handler.mobile.android.fairmondo.datalayer.businessobject.product.FairmondoCategory;

/**
 * Mutable instance of immutable FairmondoCategory.
 */
public class MutableFairmondoCategory extends FairmondoCategory {
    public MutableFairmondoCategory(FairmondoCategory immutableFairmondoCategory, ModelMapper modelMapper) {
        modelMapper.map(this, immutableFairmondoCategory);
    }

    public MutableFairmondoCategory() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAncestors(List<String> ancestors) {
        this.ancestors = ancestors;
    }
}
