package reirokusanami.handler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.registries.IForgeRegistry;
import reirokusanami.proxy.UsualProxy;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.Pattern;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.tools.TinkerTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TGCRegister {

    public static final List<ToolPart> TOOL_PARTS = new ArrayList<>();
    public static final List<ToolCore> TOOL_CORES = new ArrayList<>();
    private static final String CLIENT_PROXY = "reirokusanami.proxy.ClientProxy";
    private static final String SERVER_PROXY = "reirokusanami.proxy.ServerProxy";

    @SidedProxy(clientSide = CLIENT_PROXY)
    public static UsualProxy proxy;

    public void PartInitialization(IForgeRegistry<Item> event){

    }

    /*
     * @param _Toolpart ToolPart
     * @param Name Enter the ToolPart name.
     * @param Cost Cost required to cast ToolPart (ingot conversion)
     * @param event IForgeRegistry<Item>
     */
    protected static ToolPart RegisterParts(ToolPart _ToolPart, String Name, int Cost, IForgeRegistry<Item> event) {
        _ToolPart = new ToolPart(Material.VALUE_Ingot * Cost);
        _ToolPart.setRegistryName(Name);
        event.register(_ToolPart);
        TinkerRegistry.registerToolPart(_ToolPart);
        proxy.registerToolPartModel(_ToolPart);
        TOOL_PARTS.add(_ToolPart);

        return _ToolPart;
    }

    public void ToolInitialization(IForgeRegistry<Item> event){

    }

    /*
     * @param isAllowConfig Reference from TGCConfig
     * @param _ToolCore EXAMPLE: public static ToolCore tool_weaponHandgun = new weaponHandgun();
     * @param event RegistryEvent.Register<Item>
     */
    protected static void RegisterTools(Boolean isAllowConfig , Boolean ForgeCrafting, ToolCore _ToolCore, IForgeRegistry<Item> event) {
        if(isAllowConfig){
            event.register(_ToolCore);
            if(ForgeCrafting == true) {
                TinkerRegistry.registerToolForgeCrafting(_ToolCore);
            } else {
                TinkerRegistry.registerToolStationCrafting(_ToolCore);
            }
            proxy.registerToolModel(_ToolCore);
            TOOL_CORES.add(_ToolCore);
        }
    }

    protected static void RegisterToolBuilding() {
        for(final IToolPart Part: getTGCPart()){
            for(final ToolCore Tool: getTGCTool()){
                for(final PartMaterialType types: Tool.getRequiredComponents()) {
                    if(types.getPossibleParts().contains(Part)){
                        TinkerRegistry.registerStencilTableCrafting(Pattern.setTagForPart(new ItemStack(TinkerTools.pattern), (Item)Part));
                    }
                }
            }
        }
    }

    public static List<IToolPart> getTGCPart(){
        return Collections.unmodifiableList(TOOL_PARTS);
    }

    public static List<ToolCore> getTGCTool(){
        return Collections.unmodifiableList(TOOL_CORES);
    }

}
