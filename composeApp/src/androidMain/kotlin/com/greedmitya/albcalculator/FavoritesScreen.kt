package com.greedmitya.albcalculator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greedmitya.albcalculator.assets.loadPotionImageBitmapFromDisplayName
import com.greedmitya.albcalculator.components.AppColors
import com.greedmitya.albcalculator.model.FavoriteRecipe
import kotlinx.serialization.Serializable

@Composable
fun FavoritesScreen(
    viewModel: CraftViewModel,
    onNavigateToCraft: () -> Unit
)
 {
    val favorites = viewModel.favorites

     Column(
         modifier = Modifier
             .fillMaxSize()
             .background(AppColors.BackgroundDark)
             .padding(horizontal = 20.dp, vertical = 60.dp)
     ) {
         // ✅ Новый заголовок
         Column(
             modifier = Modifier.fillMaxWidth(),
             horizontalAlignment = Alignment.CenterHorizontally
         ) {
             Text(
                 text = "Potion Crafting",
                 fontSize = 20.sp,
                 fontWeight = FontWeight.SemiBold,
                 fontFamily = FontFamily.Serif,
                 color = AppColors.PrimaryGold
             )
             Text(
                 text = "Favorites",
                 fontSize = 16.sp,
                 fontWeight = FontWeight.Medium,
                 fontFamily = FontFamily.Serif,
                 color = AppColors.PrimaryGold
             )
         }
        Spacer(Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(favorites) { recipe ->
                FavoriteRecipeItem(
                    recipe = recipe,
                    onRemove = { viewModel.removeFromFavorites(recipe) },
                    onApply = {
                        viewModel.applyFavorite(recipe)
                        onNavigateToCraft()
                    }
                )
            }
        }
    }
}

@Composable
fun FavoriteRecipeItem(
    recipe: FavoriteRecipe,
    onRemove: () -> Unit,
    onApply: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.PanelBrown, RoundedCornerShape(8.dp))
            .padding(start = 4.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Левая часть: картинка + инфо
        Row(
            modifier = Modifier.width(240.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Image(
                bitmap = loadPotionImageBitmapFromDisplayName(
                    recipe.potionName,
                    recipe.tier,
                    recipe.enchantment
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(74.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = recipe.potionName,
                    color = AppColors.PrimaryGold,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Serif,
                    maxLines = 2,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis
                )

                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    Text(
                        text = "${recipe.tier}.${recipe.enchantment}",
                        color = AppColors.LightBeige,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = recipe.city,
                        color = AppColors.LightBeige,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.Serif
                    )

                }
            }
        }

        // Правая часть: кнопки
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(AppColors.Secondary_Beige)
                    .border(1.dp, AppColors.PrimaryGold, RoundedCornerShape(8.dp))
                    .clickable { onRemove() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_remove),
                    contentDescription = "Remove",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(46.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(AppColors.PrimaryGold)
                    .border(1.dp, AppColors.PrimaryGold, RoundedCornerShape(8.dp))
                    .clickable { onApply() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_button_pot),
                    contentDescription = "Apply",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(46.dp)
                )
            }
        }
    }
}


