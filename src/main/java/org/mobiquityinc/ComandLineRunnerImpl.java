package org.mobiquityinc;

import org.mobiquityinc.model.exception.APIException;
import org.mobiquityinc.packer.impl.SimplePacker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class ComandLineRunnerImpl implements CommandLineRunner {

    @Autowired
    private SimplePacker packer;

    @Override
    public void run(String... strings) throws Exception {
        if (strings == null || strings.length != 1) {
            throw new APIException("Wrong arguments format was provided.", null);
        }

        String filePath = strings[0];

        System.out.println(packer.pack(filePath));
    }
}