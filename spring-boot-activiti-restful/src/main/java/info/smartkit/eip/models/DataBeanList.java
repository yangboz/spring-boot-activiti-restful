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

import java.util.ArrayList;

/**
 * Contents of POJO list.
 * 
 * @author yangboz
 */
public class DataBeanList
{
    public ArrayList<DataBean> getDataBeanList()
    {
        ArrayList<DataBean> dataBeanList = new ArrayList<DataBean>();
        //
        dataBeanList.add(new DataBean(112233, "2012-05-07", "员工01", "吃吃喝喝", 5, "2012-04-27", "商家0", "i1", "说明000",
            "BB", 296));
        dataBeanList
            .add(new DataBean(22222, "2012-06-01", "USA", "AA", 5, "2012-05-27", "商家1", "i2", "说明001", "BB", 77));
        dataBeanList.add(new DataBean(3333, "2012-07-08", "한국", "AA", 5, "2012-06-27", "商家2", "i3", "说明002", "BB", 88));
        return dataBeanList;
    }

}
