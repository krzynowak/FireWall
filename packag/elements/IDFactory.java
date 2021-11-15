package packag.elements;

/* 
    Singleton factory for creation of ID objects
*/
public class IDFactory
{
    private static IDFactory instance = new IDFactory();    /* Eagerly Loading of singleton instance */
    private Integer factoryIdCounter = 0;                   /* Unique ID counter to assign all of it's creations */

    /* Objects that can be created via this factory */
    public enum IdObjects
    {
        HOST,
        GROUP,
        LIST,
        NET,
    };

    /* Disable other constructors */
    private IDFactory() {};

    /* Acces method for factory */
    public static IDFactory getInstance()
    {
        return instance;
    }

    /* Creates new IDO class based on choosen type */
    public IDO getObject(IdObjects type)
    {
        IDO newObj = null;

        switch (type) 
        {
            case NET  : { newObj = new Net(    ++this.factoryIdCounter ); break; }
            case HOST : { newObj = new Host(   ++this.factoryIdCounter ); break; }
            case GROUP: { newObj = new ACGroup(++this.factoryIdCounter ); break; }
            case LIST : { newObj = new ACList( ++this.factoryIdCounter ); break; }
        }

        return newObj;
    }

    /* Returns the count of objects created by the factory */
    public Integer getObjectCount()
    {
        return this.factoryIdCounter;
    }

}
