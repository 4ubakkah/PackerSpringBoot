package org.mobiquityinc.packer;

public interface Packer {

    /**
     * Loads list of ThingPackage from provided path and composes optimal packages.
     * @param filePath, path to file containing list of packages.
     * @return String representation of composed optimal packages
     */
    String pack(String filePath);

}
