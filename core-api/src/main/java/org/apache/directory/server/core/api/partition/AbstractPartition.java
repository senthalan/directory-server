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
package org.apache.directory.server.core.api.partition;


import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import javax.naming.InvalidNameException;

import org.apache.directory.server.core.api.partition.index.Index;
import org.apache.directory.server.core.api.partition.index.IndexNotFoundException;
import org.apache.directory.server.core.api.partition.index.MasterTable;
import org.apache.directory.server.i18n.I18n;
import org.apache.directory.shared.ldap.model.exception.LdapException;
import org.apache.directory.shared.ldap.model.exception.LdapInvalidDnException;
import org.apache.directory.shared.ldap.model.exception.LdapOtherException;
import org.apache.directory.shared.ldap.model.name.Dn;
import org.apache.directory.shared.ldap.model.schema.AttributeType;
import org.apache.directory.shared.ldap.model.schema.SchemaManager;
import org.apache.directory.shared.util.Strings;


/**
 * A {@link Partition} that helps users to implement their own partition.
 * Most methods are implemented by default.  Please look at the description of
 * each methods for the detail of implementations.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public abstract class AbstractPartition implements Partition
{
    /** <tt>true</tt> if and only if this partition is initialized. */
    protected boolean initialized;

    /** The SchemaManager instance */
    protected SchemaManager schemaManager;

    /** The partition ID */
    protected String id;

    /** The root Dn for this partition */
    protected Dn suffixDn;

    /** Default ID */
    UUID defaultID = UUID.fromString( "00000000-0000-0000-0000-000000000001" );


    /**
     * {@inheritDoc}
     */
    public void initialize() throws LdapException
    {
        if ( initialized )
        {
            // Already initialized.
            return;
        }

        try
        {
            doInit();
            initialized = true;
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            throw new LdapOtherException( e.getMessage(), e );
        }
        finally
        {
            if ( !initialized )
            {
                try
                {
                    destroy();
                }
                catch ( Exception e )
                {
                    throw new LdapOtherException( e.getMessage(), e );
                }
            }
        }
    }


    /**
     * Override this method to put your initialization code.
     */
    protected abstract void doDestroy() throws Exception;


    /**
     * Override this method to put your initialization code.
     * @throws Exception 
     */
    protected abstract void doInit() throws InvalidNameException, Exception;


    /**
     * Calls {@link #doDestroy()} where you have to put your destroy code in,
     * and clears default properties.  Once this method is invoked, {@link #isInitialized()}
     * will return <tt>false</tt>.
     */
    public final void destroy() throws Exception
    {
        try
        {
            doDestroy();
        }
        finally
        {
            initialized = false;
        }
    }


    /**
     * {@inheritDoc}
     */
    public final boolean isInitialized()
    {
        return initialized;
    }


    /**
     * {@inheritDoc}
     */
    public void setSchemaManager( SchemaManager schemaManager )
    {
        this.schemaManager = schemaManager;
    }


    /**
     * {@inheritDoc}
     */
    public final SchemaManager getSchemaManager()
    {
        return schemaManager;
    }


    /**
     * {@inheritDoc}
     */
    public final String getId()
    {
        return id;
    }


    /**
     * {@inheritDoc}
     */
    public void setId( String id )
    {
        checkInitialized( "id" );
        this.id = id;
    }


    /**
     * {@inheritDoc}
     */
    public final Dn getSuffixDn()
    {
        return suffixDn;
    }


    /**
     * {@inheritDoc}
     */
    public void setSuffixDn( Dn suffixDn ) throws LdapInvalidDnException
    {
        checkInitialized( "suffixDn" );

        this.suffixDn = suffixDn;

        if ( schemaManager != null )
        {
            this.suffixDn.apply( schemaManager );
        }
    }


    /**
     * {@inheritDoc}
     */
    public void dumpIndex( OutputStream stream, String name ) throws IOException
    {
        stream.write( Strings.getBytesUtf8( "Nothing to dump for index " + name ) );
    }


    /**
     * {@inheritDoc}
     */
    public UUID getDefaultId()
    {
        return defaultID;
    }


    /**
     * {@inheritDoc}
     */
    public UUID getRootId()
    {
        return rootID;
    }


    /**
     * {@inheritDoc}
     */
    public MasterTable getMasterTable() throws Exception
    {
        throw new UnsupportedOperationException( I18n.err( I18n.ERR_751 ) );
    }


    /**
     * {@inheritDoc}
     */
    public boolean hasIndexOn( AttributeType attributeType ) throws Exception
    {
        throw new UnsupportedOperationException( I18n.err( I18n.ERR_751 ) );
    }


    /**
     * {@inheritDoc}
     */
    public boolean hasUserIndexOn( AttributeType attributeType ) throws Exception
    {
        throw new UnsupportedOperationException( I18n.err( I18n.ERR_751 ) );
    }


    /**
     * {@inheritDoc}
     */
    public boolean hasSystemIndexOn( AttributeType attributeType ) throws Exception
    {
        throw new UnsupportedOperationException( I18n.err( I18n.ERR_751 ) );
    }


    /**
     * {@inheritDoc}
     */
    public Index<?> getIndex( AttributeType attributeType ) throws IndexNotFoundException
    {
        throw new UnsupportedOperationException( I18n.err( I18n.ERR_751 ) );
    }


    /**
     * {@inheritDoc}
     */
    public Index<?> getUserIndex( AttributeType attributeType ) throws IndexNotFoundException
    {
        throw new UnsupportedOperationException( I18n.err( I18n.ERR_751 ) );
    }


    /**
     * {@inheritDoc}
     */
    public Index<?> getSystemIndex( AttributeType attributeType ) throws IndexNotFoundException
    {
        throw new UnsupportedOperationException( I18n.err( I18n.ERR_751 ) );
    }


    /**
     * {@inheritDoc}
     */
    public boolean hasIndexOn( String oid ) throws Exception
    {
        throw new UnsupportedOperationException( I18n.err( I18n.ERR_751 ) );
    }


    /**
     * {@inheritDoc}
     */
    public boolean hasUserIndexOn( String oid ) throws Exception
    {
        throw new UnsupportedOperationException( I18n.err( I18n.ERR_751 ) );
    }


    /**
     * {@inheritDoc}
     */
    public boolean hasSystemIndexOn( String oid ) throws Exception
    {
        throw new UnsupportedOperationException( I18n.err( I18n.ERR_751 ) );
    }


    /**
     * {@inheritDoc}
     */
    public Index<?> getIndex( String oid ) throws IndexNotFoundException
    {
        throw new UnsupportedOperationException( I18n.err( I18n.ERR_751 ) );
    }


    /**
     * {@inheritDoc}
     */
    public Index<?> getUserIndex( String oid ) throws IndexNotFoundException
    {
        throw new UnsupportedOperationException( I18n.err( I18n.ERR_751 ) );
    }


    /**
     * {@inheritDoc}
     */
    public Index<?> getSystemIndex( String oid ) throws IndexNotFoundException
    {
        throw new UnsupportedOperationException( I18n.err( I18n.ERR_751 ) );
    }


    /**
     * {@inheritDoc}
     */
    public boolean updateEntryOnDnChange()
    {
        return false;
    }


    /**
     * Check that the operation is done on an initialized store
     * @param property
     */
    protected void checkInitialized( String property )
    {
        if ( initialized )
        {
            throw new IllegalStateException( I18n.err( I18n.ERR_576, property ) );
        }
    }

}
