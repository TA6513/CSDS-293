package ring;

public interface Ring<T> {
    // Returns the additive identity (0)
    T zero();
    
    // Returns the multiplicative identity (1)
    T identity();
    
    // Returns the sum of x and y
    T sum(T x, T y);
    
    // Returns the product of x and y
    T product(T x, T y);
}
