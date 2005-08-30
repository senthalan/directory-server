/*
 *   Copyright 2004 The Apache Software Foundation
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.apache.ldap.server.schema;


import org.apache.ldap.common.schema.ObjectClass;


/**
 * A do nothing adapter for an ObjectClassMonitor.  As a precaution so
 * exceptions are not lost exception based callback print stacks to stderr.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev$
 */
public class ObjectClassRegistryMonitorAdapter implements ObjectClassRegistryMonitor
{
    public void registered( ObjectClass objectClass )
    {
    }


    public void lookedUp( ObjectClass objectClass )
    {
    }


    public void lookupFailed( String oid, Throwable fault )
    {
        if ( fault != null )
        {
            fault.printStackTrace();
        }
    }


    public void registerFailed( ObjectClass objectClass, Throwable fault )
    {
        if ( fault != null )
        {
            fault.printStackTrace();
        }
    }
}
