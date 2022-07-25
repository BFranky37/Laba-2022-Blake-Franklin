import java.util.Objects;

public class CardboardBox {

    private float length;
    private float width;
    private float height;

    public CardboardBox() {
    }

    public CardboardBox(float l, float w, float h) {
        length = l;
        width = w;
        height = h;
    }

    @Override
    public String toString() {
        return "Length: " + length + " inches, Width: " + width + " inches, Height: " + height + " inches";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        CardboardBox per = (CardboardBox) obj;

        return (this.length == per.length &&
                this.width == per.width &&
                this.height == per.height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, width, height);
    }
}
