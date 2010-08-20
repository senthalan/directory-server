/*
 *   Licensed to the Apache Software Foundation (ASF) under one
 *   or more contributor license agreements.  See the NOTICE file
 *   distributed with this work for additional information
 *   regarding copyright ownership.  The ASF licenses this file
 *   to you under the Apache License, Version 2.0 (the
 *   "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 *
 */
package org.apache.directory.server.ldap.handlers;


import org.apache.directory.server.ldap.LdapSession;
import org.apache.directory.shared.ldap.message.AbandonRequest;


/**
 * AbandonRequest handler implementation.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class AbandonHandler extends LdapRequestHandler<AbandonRequest>
{
    /**
     * @see org.apache.directory.server.ldap.handlers.LdapRequestHandler#
     * handle(org.apache.directory.server.ldap.LdapSession, org.apache.directory.shared.ldap.message.Request)
     */
    public void handle( LdapSession session, AbandonRequest request ) throws Exception
    {
        int abandonedId = request.getAbandoned();

        if ( abandonedId < 0 )
        {
            return;
        }

        session.abandonOutstandingRequest( abandonedId );
    }
}
