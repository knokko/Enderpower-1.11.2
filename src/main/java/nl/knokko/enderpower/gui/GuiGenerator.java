package nl.knokko.enderpower.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import nl.knokko.enderpower.EnderPower;
import nl.knokko.enderpower.container.ContainerEnderFurnace;
import nl.knokko.enderpower.container.ContainerGenerator;
import nl.knokko.enderpower.energy.EnergyType;
import nl.knokko.enderpower.tileentity.TileEntityEnderFurnace;
import nl.knokko.enderpower.tileentity.TileEntityGenerator;

public class GuiGenerator extends GuiContainer {
	
	private static final ResourceLocation GENERATOR_GUI_TEXTURES = new ResourceLocation(EnderPower.MODID + ":textures/gui/generator.png");
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer player;
    private final TileEntityGenerator generator;

    public GuiGenerator(InventoryPlayer playerInv, TileEntityGenerator generator){
        super(new ContainerGenerator(playerInv, generator));
        this.player = playerInv;
        this.generator = generator;
        System.out.println(inventorySlots.inventorySlots);
    }
    
    protected ResourceLocation getTexture(){
    	return GENERATOR_GUI_TEXTURES;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        String s = generator.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.player.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
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
        EnergyType type = generator.getBurnType();
        if(type != null){
        	int l = getBurnProgressScaled(80);
        	drawTexturedModalRect(i + 36, j + 16, 176, 64 + 18 * type.ordinal(), l, 18);
        }
        int l = getCurrentEnergyScaled(EnergyType.THAU, 32);
        drawTexturedModalRect(i + 8, j + 80 - l, 176, 0, 16, l);
        l = getCurrentEnergyScaled(EnergyType.SIE, 32);
        drawTexturedModalRect(i + 26, j + 80 - l, 192, 0, 16, l);
        l = getCurrentEnergyScaled(EnergyType.FIE, 32);
        drawTexturedModalRect(i + 44, j + 80 - l, 208, 0, 16, l);
        l = getCurrentEnergyScaled(EnergyType.GEE, 32);
        drawTexturedModalRect(i + 62, j + 80 - l, 224, 0, 16, l);
        l = getCurrentEnergyScaled(EnergyType.DOU, 32);
        drawTexturedModalRect(i + 80, j + 80 - l, 240, 0, 16, l);
        l = getCurrentEnergyScaled(EnergyType.ENDER, 32);
        drawTexturedModalRect(i + 98, j + 80 - l, 176, 32, 16, l);
    }

    private int getBurnProgressScaled(int pixels){
        int i = generator.getField(0);
        int j = generator.getField(7);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
    
    private int getCurrentEnergyScaled(EnergyType type, int pixels){
        int i = generator.getField(1 + type.ordinal());
        long j = generator.getMaxEnergy(type);
        return (int) (j != 0 && i != 0 ? i * pixels / j : 0);
    }
}
