package de.handler.mobile.android.fairmondo.network.dto;

/**
 * Incorporates the search request
 */
public class FilterRequest {
    /**
     enumerize :condition, in: [:new, :old].
     enumerize :order_by, in: [:newest,:cheapest,:most_expensive,:old,:new,:fair,:ecologic,:small_and_precious,:most_donated]
     # => :newest,"Preis aufsteigend" => :cheapest,"Preis absteigend" => :most_expensive
    */
    public String q;
    public boolean fair;
    public boolean ecologic;
    public boolean small_and_precious;
    public boolean swappable;
    public boolean borrowable;
    public String condition;
    public int category_id;
    public String zip;
    public String order_by;
    public boolean search_in_content;
    public String price_from;
    public String price_to;
}
