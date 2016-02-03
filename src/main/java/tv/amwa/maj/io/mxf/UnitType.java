package tv.amwa.maj.io.mxf;

public enum UnitType {

	Unknown,
	HeaderClosedCompletePartitionPack,
	HeaderClosedIncompletePartitionPack,
	HeaderOpenCompletePartitionPack,
	HeaderOpenIncompletePartitionPack,
	BodyClosedCompletePartitionPack,
	BodyClosedIncompletePartitionPack,
	BodyOpenCompletePartitionPack,
	BodyOpenIncompletePartitionPack,
	FooterClosedCompletePartitionPack,
	FooterClosedIncompletePartitionPack,
	HeaderMetadata, // Assume starts with primer pack
	IndexTableSegment,
	GenericContainerEssenceElement,
	GenericContainerSystemItem,
	GenericContainerPicture,
	GenericContainerSound,
	GenericContainerData,
	GenericContainerCompound,
	ContentPackageEssenceElement,
	ContentPackageSystemItem,
	ContentPackagePicture,
	ContentPackageSound,
	ContentPackageData,
	RandomIndexPack, ;
	
	final static byte[] smpteKeyStart = new byte[] {
		0x06, 0x0e, 0x2b, 0x34, 0x02, 0x00, 0x01, 0x00, 0x0d, 0x01, 0x00, 0x01
	};
	
	final static int[] constantBytes = new int[] {
		0, 1, 2, 3, 6, 8, 9, 11
	};
	
	public final static UnitType typeFromKey(
			UL key) {
		
		byte[] keyBytes = key.getUniversalLabel();
		
		for ( int constantIndex : constantBytes ) {
			if (smpteKeyStart[constantIndex] != keyBytes[constantIndex]) 
				return Unknown;
		}
		
		switch (keyBytes[10]) {
		
		case 0x02:
			switch (keyBytes[13]) {
			
			case 0x02:
				switch (keyBytes[14]) {
				
				case 0x01:
					return HeaderOpenIncompletePartitionPack;
				case 0x02:
					return HeaderClosedIncompletePartitionPack;
				case 0x03:
					return HeaderOpenCompletePartitionPack;
				case 0x04:
					return HeaderClosedCompletePartitionPack;
				default:
					return Unknown;
				}
				
			case 0x03:
				switch (keyBytes[14]) {
				
				case 0x01:
					return BodyOpenIncompletePartitionPack;
				case 0x02:
					return BodyClosedIncompletePartitionPack;
				case 0x03:
					return BodyOpenCompletePartitionPack;
				case 0x04:
					return BodyClosedCompletePartitionPack;
				default:
					return Unknown;
				}
			case 0x04:
				switch (keyBytes[14]) {
				
				case 0x02:
					return FooterClosedIncompletePartitionPack;
				case 0x04:
					return FooterClosedCompletePartitionPack;
				default:
					return Unknown;
				}
			case 0x05:
				return HeaderMetadata;
			case 0x10:
				return IndexTableSegment;
			case 0x11:
				return RandomIndexPack;
			default:
				return Unknown;
			}
			
		case 0x03:
			switch (keyBytes[12]) {
			case 0x04: 
				return ContentPackageSystemItem;
			case 0x05:
				return ContentPackagePicture;
			case 0x06:
				return ContentPackageSound;
			case 0x07:
				return ContentPackageData;
			case 0x14:
				return GenericContainerSystemItem;
			case 0x15:
				return GenericContainerPicture;
			case 0x16:
				return GenericContainerSound;
			case 0x17:
				return GenericContainerData;
			case 0x18:
				return GenericContainerCompound;
			default:
				return Unknown;
			}
			
		default:
			return Unknown;
		}

	}
}
