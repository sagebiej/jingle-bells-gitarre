"""Draw the launcher icon and the Fire TV banner (320x180) into res/drawable."""
import os, sys
from PIL import Image, ImageDraw, ImageFont

out = os.path.join(sys.argv[1], 'res', 'drawable')
os.makedirs(out, exist_ok=True)
RED, WHITE, GOLD = (200, 16, 46), (255, 255, 255), (242, 183, 5)

def font(size):
    for f in ('/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf',
              '/usr/share/fonts/truetype/ubuntu/Ubuntu-B.ttf'):
        if os.path.exists(f):
            return ImageFont.truetype(f, size)
    return ImageFont.load_default()

def staff(d, x0, x1, y0, gap):
    for k in range(5):
        d.line([(x0, y0 + k * gap), (x1, y0 + k * gap)], fill=(255, 255, 255, 120), width=2)

b = Image.new('RGBA', (320, 180), RED)
d = ImageDraw.Draw(b)
staff(d, 0, 320, 112, 10)
for i, (x, y) in enumerate([(40, 137), (80, 127), (120, 132), (160, 142), (200, 122), (240, 127), (280, 117)]):
    d.ellipse([x - 8, y - 6, x + 8, y + 6], fill=GOLD if i == 0 else WHITE)
    d.line([(x + 7, y), (x + 7, y - 34)], fill=GOLD if i == 0 else WHITE, width=3)
d.text((160, 52), 'Jingle Bells', font=font(40), fill=WHITE, anchor='mm')
d.text((160, 90), 'Gitarre', font=font(20), fill=WHITE, anchor='mm')
b.save(os.path.join(out, 'banner.png'))

i = Image.new('RGBA', (192, 192), RED)
d = ImageDraw.Draw(i)
staff(d, 0, 192, 70, 14)
d.ellipse([70, 112, 102, 136], fill=GOLD)
d.line([(100, 124), (100, 50)], fill=GOLD, width=6)
d.text((96, 165), 'JB', font=font(30), fill=WHITE, anchor='mm')
i.save(os.path.join(out, 'icon.png'))
