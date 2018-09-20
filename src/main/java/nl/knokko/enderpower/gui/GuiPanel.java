package nl.knokko.enderpower.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import nl.knokko.enderpower.EnderPower;
import nl.knokko.enderpower.container.ContainerPanel;
import nl.knokko.enderpower.tileentity.base.TileEntityPanel;

public class GuiPanel extends GuiContainer {
	
	private final TileEntityPanel panel;
	private final InventoryPlayer player;
	private final ResourceLocation texture;

	public GuiPanel(InventoryPlayer player, TileEntityPanel panel, ResourceLocation texture) {
		super(new ContainerPanel(player, panel));
		this.panel = panel;
		this.player = player;
		this.texture = texture;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        String s = this.panel.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.player.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        int l = this.getCurrentEnergyScaled(32);
        this.drawTexturedModalRect(i + 51, j + 53 - l, 176, 32, 16, l);
    }
    
    private int getCurrentEnergyScaled(int pixels){
        int i = this.panel.getField(0);
        long j = this.panel.getMaxEnergy();
        return (int) (j != 0 && i != 0 ? i * pixels / j : 0);
    }
}
