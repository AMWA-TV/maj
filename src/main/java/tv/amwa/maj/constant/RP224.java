package tv.amwa.maj.constant;

import tv.amwa.maj.record.AUID;
import tv.amwa.maj.industry.Forge;

// Split into two as static initializer limit was exceeded
/**
 * <p>All leaf-node constants from SMPTE RP224, the <em>SMPTE Labels Registry</em>. The
 * source of the registry is avaialble at the <a href="http://www.smpte-ra.org/>SMPTE
 * Registration Authority</a>.</p>
 * 
 *
 *
 */
public interface RP224 
	extends RP224FirstHalf {

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 1080x24PsF 1920 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_1080x24PsF_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0215,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 1080x24PsF 1920 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_1080x24PsF_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0216,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 1080x24PsF 1920 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_1080x24PsF_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0217,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 1080x25P 1920 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_1080x25P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0221,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 1080x25P 1920 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_1080x25P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0222,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 1080x25P 1920 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_1080x25P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0223,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 1080x25PsF 1920 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_1080x25PsF_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0225,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 1080x25PsF 1920 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_1080x25PsF_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0226,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 1080x25PsF 1920 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_1080x25PsF_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0227,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 1080x50I 1920 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_1080x50I_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0229,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 1080x50I 1920 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_1080x50I_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x022A,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 1080x50I 1920 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_1080x50I_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x022B,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 1080x29.97P 1920 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_1080x2997P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0231,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 1080x29.97P 1920 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_1080x2997P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0232,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 1080x29.97P 1920 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_1080x2997P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0233,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 1080x29.97PsF 1920 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_1080x2997PsF_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0235,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 1080x29.97PsF 1920 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_1080x2997PsF_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0236,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 1080x29.97PsF 1920 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_1080x2997PsF_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0237,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 1080x59.94I 1920 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_1080x5994I_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0239,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 1080x59.94I 1920 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_1080x5994I_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x023A,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 1080x59.94I 1920 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_1080x5994I_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x023B,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 1080x30P 1920 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_1080x30P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0241,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 1080x30P 1920 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_1080x30P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0242,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 1080x30P 1920 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_1080x30P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0243,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 1080x30PsF 1920 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_1080x30PsF_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0245,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 1080x30PsF 1920 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_1080x30PsF_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0246,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 1080x30PsF 1920 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_1080x30PsF_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0247,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 1080x60I 1920 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_1080x60I_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0249,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 1080x60I 1920 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_1080x60I_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x024A,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 1080x60I 1920 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_1080x60I_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x024B,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 1080x50P 1920 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_1080x50P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0251,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 1080x50P 1920 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_1080x50P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0252,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 1080x50P 1920 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_1080x50P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0253,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 1080x59.94P 1920 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_1080x5994P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0259,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 1080x59.94P 1920 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_1080x5994P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x025A,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 1080x59.94P 1920 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_1080x5994P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x025B,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 1080x60P 1920 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_1080x60P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0261,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 1080x60P 1920 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_1080x60P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0262,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 1080x60P 1920 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_1080x60P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0263,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 720x23.98P 1280 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_720x2398P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0301,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 720x23.98P 1280 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_720x2398P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 720x23.98P 1280 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_720x2398P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0303,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 720x24P 1280 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_720x24P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0305,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 720x24P 1280 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_720x24P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0306,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 720x24P 1280 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_720x24P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0307,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 720x25P 1280 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_720x25P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0309,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 720x25P 1280 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_720x25P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x030A,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 720x25P 1280 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_720x25P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x030B,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 720x29.97P 1280 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_720x2997P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0311,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 720x29.97P 1280 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_720x2997P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0312,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 720x29.97P 1280 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_720x2997P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0313,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 720x30P 1280 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_720x30P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0315,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 720x30P 1280 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_720x30P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0316,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 720x30P 1280 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_720x30P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0317,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 720x50P 1280 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_720x50P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0319,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 720x50P 1280 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_720x50P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x031A,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 720x50P 1280 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_720x50P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x031B,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 720x59.94P 1280 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_720x5994P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0321,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 720x59.94P 1280 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_720x5994P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0322,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 720x59.94P 1280 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_720x5994P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0323,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 720x60P 1280 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_720x60P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0325,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 720x60P 1280 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_720x60P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0326,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 720x60P 1280 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_720x60P_1280_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0327,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed Non-standard video line format.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_Nonstandard_video_line_format = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x7F01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed Non-standard video line format.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_Nonstandard_video_line_format = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x7F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed Non-standard video line format.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_Nonstandard_video_line_format = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x7F03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Broadcast Wave audio data.</p>
     */
    public final static AUID MXFGC_Framewrapped_Broadcast_Wave_audio_data = Forge.makeAUID(
            0x0D010301, (short) 0x0206, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Broadcast Wave audio data.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Broadcast_Wave_audio_data = Forge.makeAUID(
            0x0D010301, (short) 0x0206, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped AES3 audio data.</p>
     */
    public final static AUID MXFGC_Framewrapped_AES3_audio_data = Forge.makeAUID(
            0x0D010301, (short) 0x0206, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped AES3 audio data.</p>
     */
    public final static AUID MXFGC_Clipwrapped_AES3_audio_data = Forge.makeAUID(
            0x0D010301, (short) 0x0206, (short) 0x0400,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Custom-wrapped Broadcast Wave audio data.</p>
     */
    public final static AUID MXFGC_Customwrapped_Broadcast_Wave_audio_data = Forge.makeAUID(
            0x0D010301, (short) 0x0206, (short) 0x0800,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x05 } );

    /**
     * <p>MXF-GC Custom-wrapped AES3 audio data.</p>
     */
    public final static AUID MXFGC_Customwrapped_AES3_audio_data = Forge.makeAUID(
            0x0D010301, (short) 0x0206, (short) 0x0900,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x05 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3C01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3C02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3C03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3C04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3C05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3C06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3C07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3C08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3C7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES PrivateStream1 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3D01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES PrivateStream1 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3D02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES PrivateStream1 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3D03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES PrivateStream1 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3D04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES PrivateStream1 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3D05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES PrivateStream1 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3D06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES PrivateStream1 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3D07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES PrivateStream1 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3D08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES PrivateStream1 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3D7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES PaddingStream SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3E01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES PaddingStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3E02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES PaddingStream SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3E03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES PaddingStream SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3E04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES PaddingStream SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3E05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES PaddingStream SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3E06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES PaddingStream SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3E07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES PaddingStream SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3E08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES PaddingStream SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3E7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES PrivateStream2 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3F01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES PrivateStream2 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES PrivateStream2 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3F03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES PrivateStream2 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3F04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES PrivateStream2 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3F05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES PrivateStream2 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3F06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES PrivateStream2 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3F07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES PrivateStream2 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3F08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES PrivateStream2 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x3F7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4003,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4004,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4005,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4006,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4007,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4008,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x407F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4103,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4104,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4105,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4106,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4107,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4108,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x417F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4201,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4203,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4204,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4205,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4206,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4207,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4208,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x427F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4301,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4303,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4304,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4305,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4306,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4307,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4308,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x437F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4401,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4403,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4404,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4405,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4406,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4407,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4408,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x447F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4501,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4503,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4504,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4505,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4506,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4507,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4508,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x457F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4601,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4603,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4604,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4605,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4606,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4607,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4608,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x467F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4701,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4702,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4703,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4704,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4705,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4706,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4707,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4708,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x477F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4801,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4802,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4803,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4804,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4805,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4806,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4807,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4808,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x487F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4901,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4902,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4903,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4904,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4905,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4906,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4907,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4908,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x497F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4A01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4A02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4A03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4A04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4A05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4A06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4A07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4A08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4A7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4B01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4B02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4B03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4B04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4B05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4B06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4B07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4B08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4B7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4C01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4C02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4C03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4C04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4C05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4C06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4C07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4C08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4C7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4D01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4D02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4D03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4D04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4D05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4D06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4D07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4D08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4D7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4E01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4E02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4E03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4E04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4E05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4E06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4E07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4E08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4E7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4F01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4F03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4F04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4F05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4F06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4F07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4F08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x4F7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5003,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5004,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5005,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5006,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5007,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5008,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x507F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5103,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5104,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5105,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5106,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5107,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5108,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x517F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5201,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5203,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5204,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5205,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5206,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5207,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5208,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x527F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5301,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5303,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5304,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5305,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5306,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5307,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5308,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x537F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5401,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5403,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5404,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5405,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5406,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5407,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5408,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x547F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5501,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5503,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5504,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5505,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5506,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5507,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5508,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x557F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5601,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5603,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5604,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5605,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5606,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5607,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5608,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x567F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5701,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5702,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5703,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5704,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5705,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5706,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5707,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5708,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x577F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5801,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5802,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5803,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5804,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5805,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5806,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5807,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5808,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x587F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5901,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5902,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5903,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5904,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5905,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5906,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5907,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5908,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x597F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5A01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5A02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5A03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5A04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5A05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5A06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5A07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5A08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5A7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5B01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5B02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5B03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5B04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5B05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5B06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5B07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5B08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5B7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5C01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5C02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5C03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5C04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5C05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5C06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5C07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5C08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5C7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5D01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5D02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5D03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5D04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5D05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5D06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5D07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5D08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5D7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5E01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5E02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5E03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5E04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5E05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5E06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5E07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5E08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5E7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5F01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5F03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5F04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5F05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5F06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5F07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5F08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x5F7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6003,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6004,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6005,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6006,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6007,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6008,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x607F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6103,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6104,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6105,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6106,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6107,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6108,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x617F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6201,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6203,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6204,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6205,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6206,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6207,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6208,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x627F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6301,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6303,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6304,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6305,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6306,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6307,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6308,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x637F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6401,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6403,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6404,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6405,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6406,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6407,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6408,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x647F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6501,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6503,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6504,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6505,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6506,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6507,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6508,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x657F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6601,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6603,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6604,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6605,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6606,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6607,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6608,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x667F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6701,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6702,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6703,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6704,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6705,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6706,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6707,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6708,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x677F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6801,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6802,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6803,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6804,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6805,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6806,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6807,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6808,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x687F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6901,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6902,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6903,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6904,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6905,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6906,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6907,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6908,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x697F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6A01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6A02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6A03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6A04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6A05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6A06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6A07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6A08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6A7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6B01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6B02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6B03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6B04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6B05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6B06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6B07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6B08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6B7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6C01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6C02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6C03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6C04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6C05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6C06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6C07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6C08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6C7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6D01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6D02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6D03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6D04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6D05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6D06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6D07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6D08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6D7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6E01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6E02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6E03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6E04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6E05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6E06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6E07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6E08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6E7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6F01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6F03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6F04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6F05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6F06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6F07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6F08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x6F7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES ECMStream SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES ECMStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES ECMStream SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7003,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES ECMStream SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7004,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES ECMStream SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7005,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES ECMStream SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7006,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES ECMStream SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7007,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES ECMStream SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7008,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES ECMStream SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x707F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES EMMStream SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES EMMStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES EMMStream SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7103,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES EMMStream SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7104,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES EMMStream SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7105,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES EMMStream SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7106,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES EMMStream SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7107,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES EMMStream SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7108,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES EMMStream SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x717F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7201,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7203,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7204,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7205,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7206,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7207,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7208,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x727F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES 13522Stream SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7301,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES 13522Stream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES 13522Stream SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7303,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES 13522Stream SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7304,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES 13522Stream SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7305,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES 13522Stream SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7306,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES 13522Stream SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7307,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES 13522Stream SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7308,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES 13522Stream SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x737F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7401,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7403,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7404,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7405,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7406,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7407,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7408,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x747F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7501,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7503,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7504,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7505,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7506,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7507,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7508,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x757F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7601,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7603,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7604,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7605,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7606,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7607,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7608,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x767F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7701,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7702,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7703,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7704,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7705,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7706,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7707,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7708,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x777F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7801,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7802,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7803,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7804,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7805,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7806,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7807,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7808,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x787F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES AncStream SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7901,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES AncStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7902,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES AncStream SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7903,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES AncStream SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7904,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES AncStream SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7905,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES AncStream SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7906,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES AncStream SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7907,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES AncStream SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7908,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES AncStream SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x797F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES SLPackStream SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7A01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES SLPackStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7A02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES SLPackStream SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7A03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES SLPackStream SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7A04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES SLPackStream SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7A05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES SLPackStream SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7A06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES SLPackStream SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7A07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES SLPackStream SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7A08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES SLPackStream SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7A7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7B01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7B02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7B03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7B04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7B05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7B06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7B07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7B08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7B7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-PES ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGPES_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7F01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PES ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPES_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-PES ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGPES_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7F03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-PES ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGPES_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7F04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-PES ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGPES_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7F05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice MPEG-PES ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_CustomSplice_MPEGPES_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7F06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-PES ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGPES_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7F07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-PES ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGPES_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7F08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-PES ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGPES_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0207, (short) 0x7F7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x3C02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS PrivateStream1 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x3D02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS PaddingStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x3E02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS PrivateStream2 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x3F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x4002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x4102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x4202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x4302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x4402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x4502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x4602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x4702,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x4802,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x4902,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x4A02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x4B02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x4C02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x4D02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x4E02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x4F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x5002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x5102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x5202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x5302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x5402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x5502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x5602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x5702,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x5802,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x5902,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x5A02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x5B02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x5C02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x5D02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x5E02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x5F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x6002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x6102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x6202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x6302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x6402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x6502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x6602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x6702,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x6802,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x6902,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x6A02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x6B02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x6C02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x6D02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x6E02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x6F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS ECMStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x7002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS EMMStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x7102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x7202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS 13522Stream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x7302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x7402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x7502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x7602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x7702,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x7802,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS AncStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x7902,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS SLPackStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x7A02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x7B02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-PS ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGPS_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0208, (short) 0x7F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x3C02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS PrivateStream1 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x3D02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS PaddingStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x3E02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS PrivateStream2 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x3F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x4002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x4102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x4202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x4302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x4402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x4502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x4602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x4702,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x4802,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x4902,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x4A02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x4B02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x4C02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x4D02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x4E02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x4F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x5002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x5102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x5202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x5302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x5402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x5502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x5602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x5702,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x5802,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x5902,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x5A02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x5B02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x5C02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x5D02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x5E02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x5F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x6002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x6102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x6202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x6302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x6402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x6502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x6602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x6702,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x6802,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x6902,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x6A02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x6B02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x6C02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x6D02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x6E02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x6F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS ECMStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x7002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS EMMStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x7102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x7202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS 13522Stream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x7302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x7402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x7502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x7602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x7702,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x7802,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS AncStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x7902,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS SLPackStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x7A02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x7B02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-TS ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGTS_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0209, (short) 0x7F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped A-law Audio.</p>
     */
    public final static AUID MXFGC_Framewrapped_Alaw_Audio = Forge.makeAUID(
            0x0D010301, (short) 0x020A, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MXF-GC Clip-wrapped A-law Audio.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Alaw_Audio = Forge.makeAUID(
            0x0D010301, (short) 0x020A, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MXF-GC Custom-wrapped A-law Audio.</p>
     */
    public final static AUID MXFGC_Customwrapped_Alaw_Audio = Forge.makeAUID(
            0x0D010301, (short) 0x020A, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MXF-GC Frame-wrapped Encrypted Data.</p>
     */
    public final static AUID MXFGC_Framewrapped_Encrypted_Data = Forge.makeAUID(
            0x0D010301, (short) 0x020B, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x07 } );

    /**
     * <p>MXF-GC Frame-wrapped JPEG-2000 Pictures.</p>
     */
    public final static AUID MXFGC_Framewrapped_JPEG2000_Pictures = Forge.makeAUID(
            0x0D010301, (short) 0x020C, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x07 } );

    /**
     * <p>MXF-GC Clip-wrapped JPEG-2000 Pictures.</p>
     */
    public final static AUID MXFGC_Clipwrapped_JPEG2000_Pictures = Forge.makeAUID(
            0x0D010301, (short) 0x020C, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x07 } );

    /**
     * <p>MXF-GC Generic VBI Data Mapping Undefined Payload.</p>
     */
    public final static AUID MXFGC_Generic_VBI_Data_Mapping_Undefined_Payload = Forge.makeAUID(
            0x0D010301, (short) 0x020D, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x09 } );

    /**
     * <p>MXF-GC Generic ANC Data Mapping Undefined Payload.</p>
     */
    public final static AUID MXFGC_Generic_ANC_Data_Mapping_Undefined_Payload = Forge.makeAUID(
            0x0D010301, (short) 0x020E, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x09 } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-0 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream0_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-0 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream0_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-0 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream0_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6003,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-0 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream0_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6005,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-0 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream0_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6006,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-0 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream0_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6007,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-0 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream0_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6008,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-0 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream0_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x607F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-1 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream1_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-1 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream1_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-1 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream1_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6103,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-1 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream1_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6105,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-1 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream1_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6106,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-1 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream1_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6107,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-1 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream1_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6108,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-1 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream1_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x617F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-2 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream2_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6201,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-2 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream2_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-2 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream2_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6203,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-2 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream2_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6205,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-2 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream2_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6206,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-2 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream2_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6207,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-2 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream2_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6208,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-2 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream2_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x627F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-3 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream3_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6301,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-3 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream3_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-3 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream3_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6303,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-3 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream3_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6305,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-3 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream3_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6306,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-3 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream3_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6307,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-3 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream3_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6308,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-3 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream3_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x637F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-4 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream4_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6401,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-4 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream4_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-4 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream4_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6403,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-4 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream4_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6405,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-4 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream4_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6406,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-4 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream4_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6407,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-4 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream4_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6408,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-4 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream4_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x647F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-5 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream5_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6501,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-5 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream5_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-5 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream5_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6503,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-5 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream5_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6505,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-5 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream5_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6506,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-5 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream5_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6507,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-5 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream5_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6508,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-5 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream5_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x657F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-6 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream6_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6601,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-6 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream6_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-6 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream6_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6603,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-6 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream6_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6605,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-6 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream6_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6606,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-6 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream6_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6607,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-6 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream6_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6608,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-6 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream6_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x667F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-7 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream7_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6701,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-7 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream7_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6702,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-7 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream7_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6703,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-7 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream7_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6705,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-7 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream7_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6706,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-7 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream7_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6707,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-7 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream7_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6708,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-7 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream7_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x677F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-8 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream8_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6801,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-8 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream8_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6802,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-8 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream8_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6803,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-8 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream8_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6805,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-8 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream8_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6806,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-8 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream8_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6807,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-8 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream8_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6808,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-8 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream8_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x687F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-9 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream9_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6901,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-9 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream9_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6902,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-9 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream9_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6903,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-9 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream9_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6905,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-9 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream9_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6906,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-9 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream9_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6907,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-9 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream9_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6908,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-9 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream9_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x697F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-10 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream10_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6A01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-10 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream10_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6A02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-10 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream10_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6A03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-10 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream10_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6A05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-10 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream10_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6A06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-10 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream10_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6A07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-10 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream10_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6A08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-10 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream10_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6A7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-11 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream11_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6B01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-11 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream11_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6B02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-11 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream11_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6B03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-11 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream11_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6B05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-11 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream11_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6B06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-11 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream11_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6B07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-11 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream11_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6B08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-11 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream11_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6B7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-12 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream12_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6C01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-12 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream12_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6C02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-12 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream12_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6C03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-12 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream12_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6C05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-12 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream12_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6C06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-12 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream12_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6C07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-12 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream12_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6C08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-12 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream12_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6C7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-13 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream13_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6D01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-13 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream13_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6D02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-13 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream13_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6D03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-13 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream13_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6D05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-13 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream13_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6D06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-13 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream13_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6D07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-13 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream13_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6D08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-13 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream13_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6D7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-14 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream14_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6E01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-14 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream14_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6E02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-14 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream14_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6E03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-14 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream14_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6E05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-14 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream14_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6E06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-14 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream14_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6E07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-14 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream14_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6E08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-14 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream14_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6E7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-15 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream15_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6F01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-15 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream15_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-15 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream15_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6F03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-15 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream15_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6F05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-15 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream15_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6F06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-15 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream15_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6F07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-15 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream15_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6F08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC NAL Unit Stream With VideoStream-15 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_NAL_Unit_Stream_With_VideoStream15_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x020F, (short) 0x6F7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-0 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream0_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-0 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream0_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-0 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream0_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6003,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-0 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream0_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6005,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-0 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream0_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6006,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-0 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream0_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6007,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-0 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream0_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6008,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-0 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream0_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x607F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-1 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream1_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-1 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream1_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-1 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream1_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6103,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-1 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream1_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6105,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-1 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream1_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6106,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-1 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream1_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6107,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-1 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream1_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6108,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-1 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream1_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x617F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-2 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream2_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6201,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-2 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream2_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-2 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream2_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6203,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-2 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream2_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6205,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-2 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream2_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6206,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-2 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream2_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6207,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-2 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream2_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6208,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-2 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream2_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x627F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-3 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream3_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6301,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-3 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream3_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-3 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream3_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6303,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-3 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream3_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6305,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-3 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream3_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6306,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-3 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream3_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6307,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-3 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream3_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6308,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-3 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream3_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x637F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-4 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream4_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6401,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-4 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream4_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-4 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream4_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6403,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-4 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream4_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6405,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-4 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream4_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6406,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-4 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream4_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6407,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-4 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream4_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6408,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-4 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream4_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x647F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-5 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream5_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6501,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-5 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream5_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-5 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream5_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6503,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-5 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream5_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6505,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-5 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream5_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6506,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-5 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream5_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6507,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-5 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream5_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6508,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-5 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream5_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x657F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-6 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream6_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6601,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-6 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream6_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-6 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream6_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6603,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-6 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream6_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6605,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-6 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream6_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6606,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-6 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream6_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6607,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-6 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream6_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6608,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-6 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream6_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x667F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-7 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream7_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6701,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-7 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream7_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6702,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-7 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream7_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6703,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-7 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream7_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6705,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-7 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream7_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6706,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-7 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream7_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6707,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-7 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream7_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6708,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-7 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream7_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x677F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-8 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream8_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6801,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-8 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream8_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6802,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-8 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream8_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6803,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-8 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream8_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6805,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-8 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream8_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6806,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-8 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream8_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6807,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-8 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream8_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6808,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-8 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream8_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x687F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-9 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream9_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6901,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-9 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream9_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6902,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-9 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream9_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6903,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-9 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream9_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6905,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-9 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream9_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6906,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-9 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream9_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6907,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-9 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream9_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6908,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-9 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream9_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x697F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-10 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream10_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6A01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-10 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream10_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6A02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-10 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream10_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6A03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-10 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream10_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6A05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-10 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream10_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6A06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-10 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream10_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6A07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-10 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream10_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6A08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-10 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream10_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6A7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-11 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream11_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6B01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-11 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream11_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6B02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-11 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream11_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6B03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-11 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream11_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6B05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-11 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream11_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6B06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-11 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream11_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6B07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-11 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream11_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6B08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-11 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream11_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6B7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-12 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream12_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6C01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-12 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream12_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6C02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-12 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream12_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6C03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-12 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream12_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6C05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-12 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream12_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6C06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-12 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream12_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6C07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-12 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream12_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6C08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-12 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream12_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6C7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-13 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream13_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6D01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-13 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream13_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6D02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-13 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream13_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6D03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-13 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream13_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6D05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-13 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream13_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6D06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-13 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream13_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6D07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-13 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream13_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6D08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-13 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream13_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6D7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-14 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream14_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6E01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-14 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream14_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6E02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-14 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream14_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6E03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-14 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream14_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6E05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-14 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream14_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6E06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-14 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream14_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6E07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-14 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream14_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6E08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-14 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream14_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6E7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-15 SID Frame-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream15_SID_Framewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6F01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-15 SID Clip-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream15_SID_Clipwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-15 SID CustomStripe-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream15_SID_CustomStripewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6F03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-15 SID CustomFixedAudioSize-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream15_SID_CustomFixedAudioSizewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6F05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-15 SID CustomSplice-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream15_SID_CustomSplicewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6F06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-15 SID CustomClosedGOP-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream15_SID_CustomClosedGOPwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6F07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-15 SID CustomSlave-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream15_SID_CustomSlavewrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6F08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC  AVC Byte Stream With VideoStream-15 SID CustomUnconstrained-wrapped.</p>
     */
    public final static AUID MXFGC_AVC_Byte_Stream_With_VideoStream15_SID_CustomUnconstrainedwrapped = Forge.makeAUID(
            0x0D010301, (short) 0x0210, (short) 0x6F7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC Frame-wrapped VC-3 Pictures.</p>
     */
    public final static AUID MXFGC_Framewrapped_VC3_Pictures = Forge.makeAUID(
            0x0D010301, (short) 0x0211, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC Clip-wrapped VC-3 Pictures.</p>
     */
    public final static AUID MXFGC_Clipwrapped_VC3_Pictures = Forge.makeAUID(
            0x0D010301, (short) 0x0211, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC Frame-wrapped VC-1 Pictures.</p>
     */
    public final static AUID MXFGC_Framewrapped_VC1_Pictures = Forge.makeAUID(
            0x0D010301, (short) 0x0212, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC Clip-wrapped VC-1 Pictures.</p>
     */
    public final static AUID MXFGC_Clipwrapped_VC1_Pictures = Forge.makeAUID(
            0x0D010301, (short) 0x0212, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC D-Cinema Timed Text Stream.</p>
     */
    public final static AUID MXFGC_DCinema_Timed_Text_Stream = Forge.makeAUID(
            0x0D010301, (short) 0x0213, (short) 0x0101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>MXF-GC Generic Essence Multiple Mappings.</p>
     */
    public final static AUID MXFGC_Generic_Essence_Multiple_Mappings = Forge.makeAUID(
            0x0D010301, (short) 0x027F, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MXF DMS-1 Production Framework standard.</p>
     */
    public final static AUID MXF_DMS1_Production_Framework_standard = Forge.makeAUID(
            0x0D010401, (short) 0x0102, (short) 0x0101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x04 } );

    /**
     * <p>MXF DMS-1 Production Framework extended.</p>
     */
    public final static AUID MXF_DMS1_Production_Framework_extended = Forge.makeAUID(
            0x0D010401, (short) 0x0102, (short) 0x0102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x04 } );

    /**
     * <p>MXF DMS-1 Clip Framework standard.</p>
     */
    public final static AUID MXF_DMS1_Clip_Framework_standard = Forge.makeAUID(
            0x0D010401, (short) 0x0102, (short) 0x0201,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x04 } );

    /**
     * <p>MXF DMS-1 Clip Framework extended.</p>
     */
    public final static AUID MXF_DMS1_Clip_Framework_extended = Forge.makeAUID(
            0x0D010401, (short) 0x0102, (short) 0x0202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x04 } );

    /**
     * <p>MXF DMS-1 Scene Framework standard.</p>
     */
    public final static AUID MXF_DMS1_Scene_Framework_standard = Forge.makeAUID(
            0x0D010401, (short) 0x0102, (short) 0x0301,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x04 } );

    /**
     * <p>MXF DMS-1 Scene Framework extended.</p>
     */
    public final static AUID MXF_DMS1_Scene_Framework_extended = Forge.makeAUID(
            0x0D010401, (short) 0x0102, (short) 0x0302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x04 } );

    /**
     * <p>MXF Cryptographic Framework Label.</p>
     */
    public final static AUID MXF_Cryptographic_Framework_Label = Forge.makeAUID(
            0x0D010401, (short) 0x0201, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x07 } );

    /**
     * <p>AAF Edit Protocol.</p>
     */
    public final static AUID AAF_Edit_Protocol = Forge.makeAUID(
            0x0D011201, (short) 0x0100, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x05 } );

    /**
     * <p>AAF Unconstrained OP.</p>
     */
    public final static AUID AAF_Unconstrained_OP = Forge.makeAUID(
            0x0D011201, (short) 0x0200, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x09 } );

    /**
     * <p>RIFF WAVE Container.</p>
     */
    public final static AUID RIFF_WAVE_Container = Forge.makeAUID(
            0x0D011301, (short) 0x0101, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x06 } );

    /**
     * <p>AAF Frame-wrapped JFIF Container.</p>
     */
    public final static AUID AAF_Framewrapped_JFIF_Container = Forge.makeAUID(
            0x0D011301, (short) 0x0102, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x07 } );

    /**
     * <p>AAF Clip-wrapped JFIF Container.</p>
     */
    public final static AUID AAF_Clipwrapped_JFIF_Container = Forge.makeAUID(
            0x0D011301, (short) 0x0102, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x07 } );

    /**
     * <p>AAF Clip-wrapped NITF Container.</p>
     */
    public final static AUID AAF_Clipwrapped_NITF_Container = Forge.makeAUID(
            0x0D011301, (short) 0x0103, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x07 } );

    /**
     * <p>AAF AIFF-AIFC Audio Container.</p>
     */
    public final static AUID AAF_AIFFAIFC_Audio_Container = Forge.makeAUID(
            0x0D011301, (short) 0x0104, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x07 } );

}

