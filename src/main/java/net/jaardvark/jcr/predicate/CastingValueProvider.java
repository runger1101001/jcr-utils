package net.jaardvark.jcr.predicate;

import javax.jcr.Item;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

import org.apache.jackrabbit.value.ValueFactoryImpl;

public class CastingValueProvider implements ValueProvider {

    protected String castTo;
    protected ValueProvider value;

    public CastingValueProvider(String castTo, ValueProvider value){
        this.castTo = castTo;
        this.value = value;        
    }

    
    
    @Override
    public Value getValue(Item evaluatedItem) throws RepositoryException {
        Value v = value.getValue(evaluatedItem);
        int destinationType = PropertyType.valueFromName(castTo);
        if (destinationType==v.getType())
            return v;
        switch (destinationType){
        case PropertyType.LONG:
            return ValueFactoryImpl.getInstance().createValue(v.getLong());
        case PropertyType.BOOLEAN:
            return ValueFactoryImpl.getInstance().createValue(v.getBoolean());
        case PropertyType.STRING:
            return ValueFactoryImpl.getInstance().createValue(v.getString());
        case PropertyType.DOUBLE:
            return ValueFactoryImpl.getInstance().createValue(v.getDouble());
        case PropertyType.DECIMAL:
            return ValueFactoryImpl.getInstance().createValue(v.getDecimal());
        case PropertyType.DATE:
            return ValueFactoryImpl.getInstance().createValue(v.getDate());
        default:
            throw new IllegalArgumentException("Don't know how to cast "+evaluatedItem.getPath()+" of type "+PropertyType.nameFromValue(v.getType())+" to type "+PropertyType.nameFromValue(destinationType));
        }
    }

    /**
     * Note that we currently can't cast multiple properties
     */
    @Override
    public Value[] getValues(Item p) throws RepositoryException {
        return value.getValues(p);
    }

    @Override
    public boolean isMultiple(Item p) throws RepositoryException {
        return value.isMultiple(p);
    }
    
}
