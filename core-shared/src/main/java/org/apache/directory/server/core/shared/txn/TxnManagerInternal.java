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
package org.apache.directory.server.core.shared.txn;


import org.apache.directory.server.core.api.txn.TxnManager;


/**
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
/** Package protected */
interface TxnManagerInternal extends TxnManager
{
    /**
     * Returns the current txn associated with the current thread.
     *
     * @param readOnly whether the txn is read only
     * @return current txn
     */
    Transaction getCurTxn();


    /**
     *  Returns the current version of logical data
     *
     * @return the current version of logical data
     */
    long getLogicalDataVersion();


    /**
     * Bumps the current version of logical data. Caller is 
     * assumed to provide synchronization.
     */
    void bumpLogicalDataVersion();
}
