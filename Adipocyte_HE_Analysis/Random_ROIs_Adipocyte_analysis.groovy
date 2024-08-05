//selecting tissue outline and setting some classifications that will be used later
def annoList = getAnnotationObjects()
def anno = annoList.get(0)
def tissue = anno.getROI()
def tissueClass = getPathClass('Tissue')
def roiClass = getPathClass('ROI')

//Calculate the number of ROIs that will cover 20% of the tissue 
def tissue_area = tissue.getArea()
double twenty_percent = tissue_area*0.2
double ROI_area = 2048*2048
double num_ROIs = Math.round(twenty_percent/ROI_area)
anno.each{it.setPathClass(tissueClass)} //assign classification of 'Tissue' to tissue annotation
println num_ROIs
def random_ROIs = []

//generate randomly located non-overlapping ROIs within the tissue annotation. 
//To keep them from overlapping, the tissue annotation area is continuously updated with each loop by subtracting
//the newest generated ROI from the remaining area
for (i in 1..num_ROIs) {
    randomROI = RoiTools.createRandomRectangle(tissue, 2048, 2048)
    randomAnnot = PathObjects.createAnnotationObject(randomROI)
    random_ROIs.add(randomAnnot)
    tissue = RoiTools.difference(tissue, randomROI)
    //println tissue.getArea()
}

addObjects(random_ROIs)
def ROIs = getAnnotationObjects().findAll{it.getPathClass() == null}
ROIs.each{it.setPathClass(roiClass)} //classify random ROIs as 'ROI'

//create adipocyte detections within the random ROIs
ROIs = selectObjectsByClassification('ROI')
createDetectionsFromPixelClassifier("Adipocytes", 200.0, 200.0, "SPLIT");
selectDetections();
runPlugin('qupath.lib.algorithms.IntensityFeaturesPlugin', '{"pixelSizeMicrons":0.5,"region":"ROI","tileSizeMicrons":25.0,"colorOD":true,"colorStain1":false,"colorStain2":false,"colorStain3":false,"colorRed":true,"colorGreen":true,"colorBlue":true,"colorHue":false,"colorSaturation":true,"colorBrightness":true,"doMean":true,"doStdDev":true,"doMinMax":true,"doMedian":true,"doHaralick":true,"haralickDistance":1,"haralickBins":32}');
addShapeMeasurements("AREA", "LENGTH", "CIRCULARITY", "SOLIDITY", "MAX_DIAMETER", "MIN_DIAMETER");
runObjectClassifier("Adipocytes");