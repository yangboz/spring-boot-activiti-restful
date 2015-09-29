package info.smartkit.eip;
/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */

import java.io.File;

import org.apache.directory.server.core.DefaultDirectoryService;
import org.apache.directory.server.core.DirectoryService;
import org.apache.directory.server.core.entry.ClonedServerEntry;
import org.apache.directory.server.core.filtering.EntryFilteringCursor;
import org.apache.directory.server.core.interceptor.context.AddOperationContext;
import org.apache.directory.server.core.interceptor.context.BindOperationContext;
import org.apache.directory.server.core.interceptor.context.DeleteOperationContext;
import org.apache.directory.server.core.interceptor.context.ListOperationContext;
import org.apache.directory.server.core.interceptor.context.ModifyOperationContext;
import org.apache.directory.server.core.interceptor.context.MoveAndRenameOperationContext;
import org.apache.directory.server.core.interceptor.context.MoveOperationContext;
import org.apache.directory.server.core.interceptor.context.RenameOperationContext;
import org.apache.directory.server.core.interceptor.context.SearchOperationContext;
import org.apache.directory.server.core.interceptor.context.UnbindOperationContext;
import org.apache.directory.server.core.partition.AbstractPartition;
import org.apache.directory.server.core.partition.impl.btree.jdbm.JdbmPartition;
import org.apache.directory.shared.ldap.name.LdapDN;
import org.junit.Assert;
import org.junit.Test;


public class LdapPartitionIntegrationTest {
    
    @Test
    public void startTheServerWithThePartition() throws Exception {
        DirectoryService directoryService;

        directoryService = new DefaultDirectoryService();
        directoryService.setShutdownHookEnabled(true);

        File workingDir = new File("work");
        directoryService.setWorkingDirectory(workingDir);

        // Create a new partition
        JdbmPartition helloPartition = new JdbmPartition();
        helloPartition.setId("hello");
        helloPartition.setSuffix("ou=hello");
        helloPartition.init(directoryService);

        directoryService.addPartition(helloPartition);
        directoryService.startup();
        
        ClonedServerEntry entry = directoryService.getAdminSession().lookup(new LdapDN("ou=hello"));
        Assert.assertNotNull(entry);
        
        directoryService.shutdown();
    }
}