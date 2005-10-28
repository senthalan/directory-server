/*
 *   @(#) $Id$
 *
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
package org.apache.ldap.server.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.ldap.server.protocol.ExtendedOperationHandler;
import org.apache.mina.registry.ServiceRegistry;
import org.apache.mina.registry.SimpleServiceRegistry;

/**
 * A {@link StartupConfiguration} that starts up ApacheDS with network layer support.
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev$, $Date$
 */
public class ServerStartupConfiguration extends StartupConfiguration
{
    private static final long serialVersionUID = -7138616822614155454L;

    private boolean enableNetworking = true;
    private ServiceRegistry minaServiceRegistry = new SimpleServiceRegistry();
    private int ldapPort = 389;
    private int ldapsPort = 636;
    private boolean enableKerberos;
    private boolean enableNtp;
    private final Collection extendedOperationHandlers = new ArrayList();

    protected ServerStartupConfiguration()
    {
    }

    /**
     * Returns <tt>true</tt> if networking (LDAP, LDAPS, and Kerberos) is enabled.
     */
    public boolean isEnableNetworking()
    {
        return enableNetworking;
    }

    /**
     * Sets whether to enable networking (LDAP, LDAPS, and Kerberos) or not.
     */
    public void setEnableNetworking( boolean enableNetworking )
    {
        this.enableNetworking = enableNetworking;
    }

    /**
     * Returns <tt>true</tt> if Kerberos support is enabled.
     */
    public boolean isEnableKerberos()
    {
        return enableKerberos;
    }

    /**
     * Returns <tt>true</tt> if Kerberos support is enabled.
     */
    public boolean isEnableNtp()
    {
        return enableNtp;
    }

    /**
     * Sets whether to enable Kerberos support or not.
     */
    protected void setEnableKerberos( boolean enableKerberos )
    {
        this.enableKerberos = enableKerberos;
    }

    /**
     * Sets whether to enable Ntp support or not.
     */
    protected void setEnableNtp( boolean enableNtp )
    {
        this.enableNtp = enableNtp;
    }

    /**
     * Returns LDAP TCP/IP port number to listen to.
     */
    public int getLdapPort()
    {
        return ldapPort;
    }

    /**
     * Sets LDAP TCP/IP port number to listen to.
     */
    protected void setLdapPort( int ldapPort )
    {
        ConfigurationUtil.validatePortNumber( ldapPort );
        this.ldapPort = ldapPort;
    }

    /**
     * Returns LDAPS TCP/IP port number to listen to.
     */
    public int getLdapsPort()
    {
        return ldapsPort;
    }

    /**
     * Sets LDAPS TCP/IP port number to listen to.
     */
    protected void setLdapsPort( int ldapsPort )
    {
        ConfigurationUtil.validatePortNumber( ldapsPort );
        this.ldapsPort = ldapsPort;
    }

    /**
     * Returns <a href="http://directory.apache.org/subprojects/network/">MINA</a>
     * {@link ServiceRegistry} that will be used by ApacheDS.
     */
    public ServiceRegistry getMinaServiceRegistry()
    {
        return minaServiceRegistry;
    }

    /**
     * Sets <a href="http://directory.apache.org/subprojects/network/">MINA</a>
     * {@link ServiceRegistry} that will be used by ApacheDS.
     */
    protected void setMinaServiceRegistry( ServiceRegistry minaServiceRegistry )
    {
        if( minaServiceRegistry == null )
        {
            throw new ConfigurationException( "MinaServiceRegistry cannot be null" );
        }
        this.minaServiceRegistry = minaServiceRegistry;
    }
    
    public Collection getExtendedOperationHandlers()
    {
        return new ArrayList( extendedOperationHandlers );
    }
    
    protected void setExtendedOperationHandlers( Collection handlers )
    {
        for( Iterator i = handlers.iterator(); i.hasNext(); )
        {
            if( !( i.next() instanceof ExtendedOperationHandler ) )
            {
                throw new IllegalArgumentException(
                        "The specified handler collection contains an element which is not an ExtendedOperationHandler." );
            }
        }
        
        this.extendedOperationHandlers.clear();
        this.extendedOperationHandlers.addAll( handlers );
    }
}
