package nl.knokko.enderpower.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import nl.knokko.enderpower.EnderPower;
import nl.knokko.enderpower.container.ContainerEnderFurnace;
import nl.knokko.enderpower.tileentity.TileEntityEnderFurnace;

public class GuiEnderFurnace extends GuiContainer {

	private static final ResourceLocation ENDER_FURNACE_GUI_TEXTURES = new ResourceLocation(EnderPower.MODID + ":textures/gui/ender_furnace.png");
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private final TileEntityEnderFurnace inventory;

    public GuiEnderFurnace(InventoryPlayer playerInv, TileEntityEnderFurnace furnaceInv){
        super(new ContainerEnderFurnace(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.inventory = furnaceInv;
    }
    
    protected ResourceLocation getTexture(){
    	return ENDER_FURNACE_GUI_TEXTURES;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        String s = this.inventory.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(getTexture());
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        int l = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
        l = this.getCurrentEnergyScaled(32);
        this.drawTexturedModalRect(i + 21, j + 53 - l, 176, 32, 16, l);
    }

    private int getCookProgressScaled(int pixels){
        int i = this.inventory.getField(0);
        int j = this.inventory.getField(1);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
    
    private int getCurrentEnergyScaled(int pixels){
        int i = this.inventory.getField(2);
        long j = this.inventory.getMaxEnergy();
        return (int) (j != 0 && i != 0 ? i * pixels / j : 0);
    }
}
