import java.util.UUID;

/**
 * @author Liu Xiong
 * @date 14/12/2023 下午1:50
 */
public class test {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(UUID.randomUUID().toString().replace("-", ""));
        }
    }
}
