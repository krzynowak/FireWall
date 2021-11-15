
package packag.elements;

/* Base class for all objects to be assigned ID */
public abstract class IDO
{
    /* Set at creation and should be unique */
    private final Integer Id;

    /* Base constructor to be called from factory */
    protected IDO(Integer Identifier)
    {
        this.Id = Identifier;
    }

    /* For identification */
    public Integer getId()
    {
        return this.Id;
    }

    /* Match method required for all objects inheriting this */
    public abstract boolean matchDatagram(long ip);
}
