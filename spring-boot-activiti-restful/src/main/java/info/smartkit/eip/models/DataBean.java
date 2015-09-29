/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * All rights reserved.
 */
package info.smartkit.eip.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contents of POJO file.
 * 
 * @author yangboz
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataBean
{
    public DataBean(long expenses_id, String expenses_owner, String expenses_name, String expenses_date,
        long expenses_invoices_num, String items_date, String items_vendors, String items_invoices, String items_notes,
        String items_participantIds, long items_amount)
    {
        this.expenses_id = expenses_id;
        this.expenses_date = expenses_date;
        this.expenses_name = expenses_name;
        this.expenses_owner = expenses_owner;
        this.expenses_invoices_num = expenses_invoices_num;

        this.items_date = items_date;
        this.items_invoices = items_invoices;
        this.items_notes = items_notes;
        this.items_participantIds = items_participantIds;
        this.items_vendors = items_vendors;
        this.items_amount = items_amount;
    }

    public DataBean()
    {

    }

    @JsonProperty("expenses_id")
    private long expenses_id;

    public long getExpenses_id()
    {
        return expenses_id;
    }

    public void setExpenses_id(long expenses_id)
    {
        this.expenses_id = expenses_id;
    }

    @JsonProperty("expenses_owner")
    private String expenses_owner;

    public String getExpenses_owner()
    {
        return expenses_owner;
    }

    public void setExpenses_owner(String expenses_owner)
    {
        this.expenses_owner = expenses_owner;
    }

    @JsonProperty("expenses_name")
    private String expenses_name;

    public String getExpenses_name()
    {
        return expenses_name;
    }

    public void setExpenses_name(String expenses_name)
    {
        this.expenses_name = expenses_name;
    }

    @JsonProperty("expenses_date")
    private String expenses_date;

    public String getExpenses_date()
    {
        return expenses_date;
    }

    public void setExpenses_date(String expenses_date)
    {
        this.expenses_date = expenses_date;
    }

    @JsonProperty("expenses_invoices_num")
    private long expenses_invoices_num;

    public long getExpenses_invoices_num()
    {
        return expenses_invoices_num;
    }

    public void setExpenses_invoices_num(long expenses_invoices_num)
    {
        this.expenses_invoices_num = expenses_invoices_num;
    }

    @JsonProperty("items_date")
    private String items_date;

    public String getItems_date()
    {
        return items_date;
    }

    public void setItems_date(String items_date)
    {
        this.items_date = items_date;
    }

    @JsonProperty("items_vendors")
    private String items_vendors;

    public String getItems_vendors()
    {
        return items_vendors;
    }

    public void setItems_vendors(String items_vendors)
    {
        this.items_vendors = items_vendors;
    }

    @JsonProperty("items_notes")
    private String items_notes;

    public String getItems_notes()
    {
        return items_notes;
    }

    public void setItems_notes(String items_notes)
    {
        this.items_notes = items_notes;
    }

    @JsonProperty("items_invoices")
    private String items_invoices;

    public String getItems_invoices()
    {
        return items_invoices;
    }

    public void setItems_invoices(String items_invoices)
    {
        this.items_invoices = items_invoices;
    }

    @JsonProperty("items_participantIds")
    private String items_participantIds;

    public String getItems_participantIds()
    {
        return items_participantIds;
    }

    public void setItems_participantIds(String items_participantIds)
    {
        this.items_participantIds = items_participantIds;
    }

    @JsonProperty("items_amount")
    private long items_amount;

    public long getItems_amount()
    {
        return items_amount;
    }

    public void setItems_amount(long items_amount)
    {
        this.items_amount = items_amount;
    }

}
