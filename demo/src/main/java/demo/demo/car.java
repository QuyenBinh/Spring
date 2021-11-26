package demo.demo;

import org.springframework.stereotype.Component;

@Component
public class car implements engine{
    @Override
    public void Run()   {
        System.out.println("Xe chay dong co trung quoc nen rat la sida");
    }
}
